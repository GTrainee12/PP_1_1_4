import java.util.Random;
import service.UserService;
import service.UserServiceImpl;

public class Main {
    private final static UserService userService = new UserServiceImpl();


    public static void main(String[] args) {
        userService.createUsersTable();
        createRandomUsers(6);
        userService.removeUserById(4);
        userService.getAllUsers();
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }

    private static void createRandomUsers(int numberOfUsers) {
        Random random = new Random();
        for (int i = 0; i < numberOfUsers; i++) {
            String name = generateRandomString(random, 7);
            String lastName = generateRandomString(random, 10);
            byte age = (byte) random.nextInt(100);
            userService.saveUser(name, lastName, age);
        }
    }

    private static String generateRandomString(Random random, int length) {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(characters.length());
            sb.append(characters.charAt(index));
        }
        return sb.toString();
    }
}
