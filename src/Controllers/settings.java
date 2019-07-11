package Controllers;

import java.util.IdentityHashMap;

public interface settings {
    public String siteHelp = "http://localhost/medart/news.html";
    public String domain = "www.google.com";

    IdentityHashMap<String, Boolean> login = new IdentityHashMap<>();
    IdentityHashMap<String, String> user = new IdentityHashMap<>();
    IdentityHashMap<String, String> id = new IdentityHashMap<>();

    IdentityHashMap<String, String> hospital = new IdentityHashMap<>();
    IdentityHashMap<String, Boolean> changepassword = new IdentityHashMap<>();
    String[] des = {"jdbc:mysql://127.0.0.1/edoc_hospitals", "root", ""};
//    String[] des = {"jdbc:mysql://nanotechsoftwares.co.ke:3306/nanotech_HospitalSystem", "nanotech_admin", ",4=y4,Zv6hR}"};


    String appName = "Medica ";
    public static final String APPLICATION_ICON =
            "resources/images/logo.png";
}
