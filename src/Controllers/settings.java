package Controllers;

import java.util.IdentityHashMap;

public interface settings {
    String siteHelp = "http://localhost/medart/news.html";
    String domain = "www.google.com";

    IdentityHashMap<String, Boolean> login = new IdentityHashMap<>();
    IdentityHashMap<String, String> user = new IdentityHashMap<>();
    IdentityHashMap<String, String> name = new IdentityHashMap<>();
    IdentityHashMap<String, String> id = new IdentityHashMap<>();
    String localDb = "jdbc:sqlite:sessionLocalDb.conf";
    IdentityHashMap<String, String> hospital = new IdentityHashMap<>();
    IdentityHashMap<String, Boolean> changepassword = new IdentityHashMap<>();
    String[] des = {"jdbc:mysql://127.0.0.1/edoc_hospitals", "root", ""};
//    String[] des = {"jdbc:mysql://nanotechsoftwares.co.ke:3306/nanotech_HospitalSystem", "nanotech_admin", ",4=y4,Zv6hR}"};

    String appName = "Medica ";
    String APPLICATION_ICON = "resources/images/logo.png";
}
