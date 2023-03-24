package at.spengergasse.friseursalon.presentation.web;

import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.service.ServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Controller
@RequestMapping(ServiceController.BASE_ROUTE)

public class ServiceController implements ControllerSupport{

    final static String BASE_ROUTE = "/services";
    final static String TEMPLATE_BASE_DIR = "services/";
    public final ServiceService serviceService;

    @GetMapping
    public String getAllServices(Model model) {

        List<Service> services = serviceService.getAllServices();
        log.debug("Found {} services in getAllServices()", services.size());
        model.addAttribute("services", services);
//        return "reservations/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
        return template("index");   // return index.html
    }

    @GetMapping("/{key}")
    public String getService(@PathVariable String key, Model model) {

        Optional<Service> service = serviceService.findByKey(key);
        if (service.isEmpty()) return redirect(BASE_ROUTE);
        model.addAttribute("service", service.get());
//        return "reservations/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @Override
    public String getTemplateBaseDir() {
        return TEMPLATE_BASE_DIR;
    }
}

