package at.spengergasse.friseursalon.service.commands;


public record CreateBarberCommand(String nickName,
                                  String firstName,
                                  String lastName) {
}
