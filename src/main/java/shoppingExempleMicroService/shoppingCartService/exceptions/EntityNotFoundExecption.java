package shoppingExempleMicroService.shoppingCartService.exceptions;

public class EntityNotFoundExecption extends RuntimeException {

	public EntityNotFoundExecption(String  message ) {
		super(message);
	}
}
