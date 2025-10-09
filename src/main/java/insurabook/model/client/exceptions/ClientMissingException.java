package insurabook.model.client.exceptions;

public class ClientMissingException extends RuntimeException {
  public ClientMissingException() {
    super("Client does not exist!");
  }
}
