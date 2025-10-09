package insurabook.model.client.exceptions;

public class ClientDuplicateException extends RuntimeException {
  public ClientDuplicateException() {
    super("Client already exists!");
  }
}
