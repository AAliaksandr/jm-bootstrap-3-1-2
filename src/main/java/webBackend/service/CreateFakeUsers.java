package webBackend.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import webBackend.model.Role;
import webBackend.model.User;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CreateFakeUsers {
    private final UserService userService;
    private final RoleService roleService;

    private  final String namesUri = "/filling/names.txt";
    private  final String lastNamesUri = "/filling/last-names.txt";
    private  final String colorUri = "/filling/colors.txt";
    private  final String carLabelsUri = "/filling/car-labels.txt";
    private  final String regNumbersUri = "/filling/car-registration-numbers.txt";

    public CreateFakeUsers(UserService userService, RoleService roleService){
        this.userService = userService;
        this.roleService = roleService;
    }

    public   List<String> getNames() throws IOException {
        List<String>  maleNames = Files.lines(new ClassPathResource(namesUri).getFile().toPath())
                .collect(Collectors.toList());

        return maleNames;
    }

    public   List<String> getLastNames() throws IOException {
        List<String>  maleLastNames = Files.lines(new ClassPathResource(lastNamesUri).getFile().toPath())
                .collect(Collectors.toList());

        return maleLastNames;
    }

    public String transliterate(String message){
        char[] abcCyr =   {' ','а','б','в','г','д','е','ё', 'ж','з','и','й','к','л','м','н','о','п','р','с','т','у','ф','х', 'ц','ч', 'ш','щ','ъ','ы','ь','э', 'ю','я','А','Б','В','Г','Д','Е','Ё', 'Ж','З','И','Й','К','Л','М','Н','О','П','Р','С','Т','У','Ф','Х', 'Ц', 'Ч','Ш', 'Щ','Ъ','Ы','Ь','Э','Ю','Я','a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z','A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        String[] abcLat = {" ","a","b","v","g","d","e","e","zh","z","i","y","k","l","m","n","o","p","r","s","t","u","f","h","ts","ch","sh","sch", "","i", "","e","ju","ja","A","B","V","G","D","E","E","Zh","Z","I","Y","K","L","M","N","O","P","R","S","T","U","F","H","Ts","Ch","Sh","Sch", "","I", "","E","Ju","Ja","a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z","A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z"};
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < message.length(); i++) {
            for (int x = 0; x < abcCyr.length; x++ ) {
                if (message.charAt(i) == abcCyr[x]) {
                    builder.append(abcLat[x]);
                }
            }
        }
        return builder.toString();
    }

    public  void createFakeUsers() throws IOException {

//        Role userRole = new Role("USER");
//        Role adminRole = new Role("ADMIN");
            roleService.addRole(new Role("USER"));
            roleService.addRole(new Role("ADMIN"));



        for(long i = 1L; i <= 9; i++) {
            User user = new User(getNames().get((int) i),
                    getLastNames().get((int) i),
                    i,
                    this.transliterate(getNames().get((int) i)) + "@mail.ru",
                    "123456");
            List<Role> roles = new ArrayList<>();
            Role role = new Role();
            role.setRole("USER");
            roles.add(role);
            user.setRoles(roles);
            userService.addUser(user);
        }
        User user2 = new User("user", "de user", 32, "user@mail.ru", "user");
        List<Role> roles2 = new ArrayList<>();
        Role role2 = new Role();
        role2.setRole("USER");
        roles2.add(role2);
        user2.setRoles(roles2);
        userService.addUser(user2);

        User fakeAdmin = new User("admin", "de admin", 42, "admin@mail.ru", "admin");
        List<Role> roles3 = new ArrayList<>();
        Role role3 = new Role();
        role3.setRole("ADMIN");
        roles3.add(role3);
        fakeAdmin.setRoles(roles3);
        userService.addUser(fakeAdmin);
        

    }
}