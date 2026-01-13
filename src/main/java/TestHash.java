import util.PasswordUtil;

public class TestHash {

    public static void main(String[] args) {

        String plainPassword = "aaaaaaaa";

        String hashedPassword = PasswordUtil.hash(plainPassword);

        System.out.println("Plain : " + plainPassword);
        System.out.println("Hash  : " + hashedPassword);
    }
}
