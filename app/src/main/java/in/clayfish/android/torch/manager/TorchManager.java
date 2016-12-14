package in.clayfish.android.torch.manager;

/**
 * This interface is what is used by this app. Other complexity of actual control of flash is hidden by it.
 *
 * @author shuklaalok7
 * @since 14/12/2016
 */
public interface TorchManager {

    /**
     * Turn the flash off
     */
    void turnOff();

    /**
     * Turn the flash on
     */
    void turnOn();

    /**
     * Toggle: if on then turn off, if off then turn on
     */
    void toggle();

    /**
     * This method is to report status
     *
     * @return true if the torch is shining
     */
    boolean isFlashOn();

}
