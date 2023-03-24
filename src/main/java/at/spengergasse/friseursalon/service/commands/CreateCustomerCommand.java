package at.spengergasse.friseursalon.service.commands;

public record CreateCustomerCommand(String userName,
                                    String password,
                                    String firstName,
                                    String lastName,
                                    String billingAddressStreetNumber,
                                    String billingAddressZipCode,
                                    String billingAddressCity,
                                    String billingAddressCountryIso2Code)  {
}
