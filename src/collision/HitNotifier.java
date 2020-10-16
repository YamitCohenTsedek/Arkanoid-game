package collision;


/**
 * The HitNotifier interface is an interface in which objects that are responsible for
 *  notifying about hit events should implement.
 */
public interface HitNotifier {

    /**
     * Objects that should be notified of hit events, register themselves to the HitNotifier by addHitListener method.
     * @param hl the listener that should be notified about the hit events of the HitNotifier.
     */
    void addHitListener(HitListener hl);

    /**
     * Remove hl from the list of the listeners of HitNotifier.
     * @param hl a listener that should be removed from the list of the hit listeners.
     */
    void removeHitListener(HitListener hl);
}