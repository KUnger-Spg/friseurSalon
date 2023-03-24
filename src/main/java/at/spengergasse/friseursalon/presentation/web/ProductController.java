package at.spengergasse.friseursalon.presentation.web;

import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.domain.Service;
import at.spengergasse.friseursalon.service.ProductService;
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
@RequestMapping(ProductController.BASE_ROUTE)

public class ProductController implements ControllerSupport{

    final static String BASE_ROUTE = "/products";
    final static String TEMPLATE_BASE_DIR = "products/";
    public final ProductService productService;

    @GetMapping
    public String getAllProducts(Model model) {

        List<Product> products = productService.getAllProducts();
        log.debug("Found {} products in getAllProducts()", products.size());
        model.addAttribute("products", products);
//        return "reservations/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
        return template("index");   // return index.html
    }

    @GetMapping("/{key}")
    public String getProduct(@PathVariable String key, Model model) {

        Optional<Product> product = productService.findByKey(key);
        if (product.isEmpty()) return redirect(BASE_ROUTE);
        model.addAttribute("product", product.get());
//        return "reservations/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @Override
    public String getTemplateBaseDir() {
        return TEMPLATE_BASE_DIR;
    }
}

