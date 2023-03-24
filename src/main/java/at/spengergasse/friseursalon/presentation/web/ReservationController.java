package at.spengergasse.friseursalon.presentation.web;

import at.spengergasse.friseursalon.domain.Reservation;
import at.spengergasse.friseursalon.service.CustomerService;
import at.spengergasse.friseursalon.service.ReservationService;
import at.spengergasse.friseursalon.service.forms.AddReservationForm;
import at.spengergasse.friseursalon.service.forms.EditReservationForm;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Log4j2

@Controller
@RequestMapping(ReservationController.BASE_ROUTE)

public class ReservationController implements ControllerSupport{

    final static String BASE_ROUTE = "/reservations";
    final static String TEMPLATE_BASE_DIR = "reservations/";

    public final ReservationService reservationService;
    public final CustomerService customerService;

    @GetMapping
    public String getAllReservations(Model model) {
//Syntax Prüfung des Inputs , Input Verarbeitung, Daten Beschaffung, Logik
        List<Reservation> reservations = reservationService.getAllReservations();
        log.debug("Found {} reservations in getAllReservations()", reservations.size());
        if (reservations.size() == 1) {
            model.addAttribute("reservation", reservations.get(0));
            return template("detail");
        }
        model.addAttribute("reservations", reservations);
        return template("index");   // return index.html
//        return "reservations/index";   // return index.html
//        return TEMPLATE_BASE_DIR+"index";   // return index.html
    }

//    @GetMapping("/{key}")
    @GetMapping(KEY_PATH_VAR)
    public String getReservation(@PathVariable String key, Model model) {

        Optional<Reservation> reservation = reservationService.findByKey(key);
        if (reservation.isEmpty()) return redirect(BASE_ROUTE);
        model.addAttribute("reservation", reservation.get());
//        return "reservations/detail";   // return detail.html
//        return TEMPLATE_BASE_DIR+"detail";   // return detail.html
        return template("detail");   // return detail.html
    }

    @GetMapping(ADD_PATH)
    public String showAddReservationForm(Model model) {
        populateCustomers (model);
//        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("addReservationForm", new AddReservationForm());
        return template("addForm");
    }

    @PostMapping(ADD_PATH)
    public String handleAddReservationForm(Model model, @Valid @ModelAttribute AddReservationForm addReservationForm,
                                           BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            populateCustomers(model);
            return template("addForm");
        }
        reservationService.createReservation(addReservationForm.getReservationName(), addReservationForm.getCustomerKey());
        return redirect(BASE_ROUTE);
    }

    @GetMapping(EDIT_PATH)
    public String showEditReservationForm(Model model, @PathVariable String key) {
        Optional<Reservation> reservation= reservationService.findByKey(key);

        if (reservation.isPresent()) {
            Reservation r = reservation.get();
            model.addAttribute("key", key);
            model.addAttribute("editReservationForm", EditReservationForm.builder()
                    .reservationName(r.getReservationName())
                    .customerKey(r.getCustomer().getKey())
                    .build());

            populateCustomers(model);
            return template("editForm");
        } else {
            //logging
            return redirect(BASE_ROUTE);
        }
    }

    @PostMapping(EDIT_PATH)
    public String handleEditReservationForm(Model model,
                                            @PathVariable String key,
                                            @Valid @ModelAttribute EditReservationForm editReservationForm,
                                            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            populateCustomers(model);
            return template("editForm");
        }

        reservationService.updateReservation(key, editReservationForm.getReservationName(),
                editReservationForm.getCustomerKey());
        return redirect(BASE_ROUTE);
    }

//    @GetMapping("/{key}/delete")
    @GetMapping(DELETE_PATH)
    public String deleteReservation(@PathVariable String key) {

    reservationService.deleteByKey(key);
            return redirect(BASE_ROUTE);
    }

    private void populateCustomers(Model model) {
        model.addAttribute("customers", customerService.getAllCustomers());
    }

    @Override
    public String getTemplateBaseDir() {

        return TEMPLATE_BASE_DIR;
    }
}

