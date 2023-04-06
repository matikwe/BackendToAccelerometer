package org.example.user;

import org.example.salt.Salt;
import org.example.salt.SaltRepository;
import org.example.utils.PasswordUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final SaltRepository saltRepository;
    private final static String ID = "id";
    private final static String TOTAL_JUMPS = "totalJumps";
    private final static String ZERO = "0";

    @Autowired
    public UserService(UserRepository userRepository, SaltRepository saltRepository) {
        this.userRepository = userRepository;
        this.saltRepository = saltRepository;
    }


    public List<User> getUsers() {
        return userRepository.findAll();
    }

    public Map<String, String> addNewUser(User user) {
        Optional<User> userByLogin = userRepository.findUserByLogin(user.getLogin());
        if (userByLogin.isPresent()) {
            throw new IllegalStateException("login: " + user.getLogin() + " exists!");
        }

        if (isValidPassword(user.getPassword()).length() > 0) {
            throw new IllegalStateException(isValidPassword(user.getPassword()));
        }

        user.setSalt(generateSalt(user));
        user.setPassword(generateSecurePassword(user.getPassword(), user.getSalt()));
        userRepository.save(user);
        Map<String, String> details = new HashMap<>();
        Long totalJumps = user.getTotalJumps();
        details.put(ID, user.getId().toString());
        details.put(TOTAL_JUMPS, totalJumps > 0 ? totalJumps.toString() : ZERO);
        return details;
    }

    public Map<String, String> verifyLoginDetails(String login, String password) {
        Optional<User> getUserByLogin = userRepository.findUserByLogin(login);
        if (getUserByLogin.isPresent()) {
            Optional<User> getUserByCheckLoginAndPassword = Optional.ofNullable(userRepository.checkLoginAndPassword(login, generateSecurePassword(password, getUserByLogin.get().getSalt()))
                    .orElseThrow(() -> new IllegalStateException(
                            "Login or password is incorrect."
                    )));
            Map<String, String> details = new HashMap<>();
            if (getUserByCheckLoginAndPassword.isPresent()) {
                Long totalJumps = getUserByCheckLoginAndPassword.get().getTotalJumps();
                details.put(ID, getUserByCheckLoginAndPassword.get().getId().toString());
                details.put(TOTAL_JUMPS, totalJumps > 0 ? totalJumps.toString() : ZERO);
            }

            return details;
        } else {
            throw new IllegalStateException("Login is incorrect.");
        }
    }

    @Transactional
    public void editTotalJumps(Long userId, Long totalJumps) {
        User user = userRepository.getUserById(userId);
        if (!Objects.equals(totalJumps, user.getTotalJumps())) {
            user.setTotalJumps(totalJumps);
        }
    }

    private String generateSecurePassword(String password, Long salt_id) {
        Optional<Salt> salt = saltRepository.checkExistSalt(salt_id);
        return salt.map(value -> PasswordUtils.generateSecurePassword(password, value.getSalt())).orElse(null);
    }

    private Salt generateSalt(User user) {
        Salt newSalt = new Salt(PasswordUtils.getSalt(30));
        saltRepository.save(newSalt);
        user.setSalt(newSalt);
        return newSalt;
    }

    private String isValidPassword(String password) {
        List<String> regex = getRegex();
        List<String> messageList = getMessage();
        StringBuilder message = new StringBuilder();
        if (password == null || password.isEmpty()) {
            return "Enter your password.";
        }
        for (String mess : messageList) {
            if (!checkRegex(password, regex.get(messageList.indexOf(mess)))) {
                message.append(mess);
            }
        }
        return message.toString();
    }

    private boolean checkRegex(String password, String regex) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        return m.matches();
    }

    private List<String> getRegex() {
        List<String> regex = new ArrayList<>();
        regex.add(".*[0-9]{1,}.*");
        regex.add(".*[a-z]{1,}.*");
        regex.add(".*[A-Z]{1,}.*");
        regex.add(".*[@#$%^&+=]{1,}.*");
        regex.add("^(?=\\S+$).{8,20}$");
        return regex;
    }

    private List<String> getMessage() {
        List<String> messageList = new ArrayList<>();
        messageList.add("The password must contain a number. ");
        messageList.add("The password must contain a lowercase character. ");
        messageList.add("The password must contain a capital character. ");
        messageList.add("The password must contain a special character. ");
        messageList.add("The password must contain between 8 and 20 characters. ");
        return messageList;
    }
}
