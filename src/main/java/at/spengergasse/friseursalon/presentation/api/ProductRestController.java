package at.spengergasse.friseursalon.presentation.api;


import at.spengergasse.friseursalon.domain.Product;
import at.spengergasse.friseursalon.service.ProductService;
import at.spengergasse.friseursalon.service.commands.CreateProductCommand;
import at.spengergasse.friseursalon.service.dtos.ProductDto;
import at.spengergasse.friseursalon.service.dtos.ServiceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor

@RestController
// @RequestMapping( "/api/products")
@RequestMapping( ApiConstants.API+"/products")

public class ProductRestController {

    private final ProductService productService;

/*
    @GetMapping({ "", "/"})
    public HttpEntity<List<ProductDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts()
                .stream()
                .map(ProductDto::new)
                .toList()) ;
    }
*/

    @GetMapping({ "", "/"})  //?expand=customer; customer.address
    public HttpEntity<CollectionModel<ProductDto>> getAllProducts() {

        List<ProductDto> dtoList = productService.getAllProducts().stream().map(this::toDto).toList();
        if (dtoList.isEmpty()) return ResponseEntity.noContent().build();
        CollectionModel<ProductDto> productDtos = CollectionModel.of(dtoList).add(createCollectionLink());

        return ResponseEntity.ok(productDtos);
    }

    @PostMapping( { "", "/"} )
    public HttpEntity<ProductDto> createProduct(@Valid @RequestBody CreateProductCommand cmd) {

        Product product = productService.createProduct(cmd);
        ProductDto dto = toDto(product);
        Link _self = dto.getLink(IanaLinkRelations.SELF).get();

        return ResponseEntity.created(_self.toUri()).body(dto);
    }

    @GetMapping( "/{key}")
    public HttpEntity<ProductDto> getProduct(@PathVariable String key) {
//        return reservationService.findReservationByReservationName(key)
        return productService.findByKey(key)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    protected ProductDto toDto (Product product) {
        var _self = linkTo(methodOn(ProductRestController.class).getProduct(product.getKey()));
        return new ProductDto(product)
                .add(_self.withSelfRel())
                .add(createCollectionLink());
    }

    private Link createCollectionLink() {
        return linkTo(methodOn(ProductRestController.class).getAllProducts()).withRel(IanaLinkRelations.COLLECTION);
    }

}

