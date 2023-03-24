package at.spengergasse.friseursalon.presentation.web;

public interface ControllerSupport {

    final static String ADD_PATH = "/add";
    //    final static String ADD_ROUTE = BASE_ROUTE + ADD_PATH;  // /reservations/add
    final static String KEY_PATH_VAR = "/{key}";
    final static String EDIT_PATH = KEY_PATH_VAR + "/edit";
    final static String DELETE_PATH = KEY_PATH_VAR + "/delete";

    String getTemplateBaseDir();

    default String redirect(String route) {
        // Fehlerbehandlung für fehlende Route oder wenn Route nicht mit einem / anfängt
        return "redirect:%s".formatted(route);
    }

    default String forward(String route) {
        // Fehlerbehandlung für fehlende Route oder wenn Route nicht mit einem / anfängt
        return "forward:%s".formatted(route);
    }

    default String template(String name) {
        // Fehlerbehandlung für fehlenden Namen oder wenn Name mit einem / anfängt
        return getTemplateBaseDir()+name;
    }
}
