package net.riley.riley_mod.world.domain;


public final class RileyModDomains {
    private RileyModDomains() {}

    public static void registerAll() {
        DomainRegistry.register(new BlizzardDomainBehavior());
        DomainRegistry.register(new HuricaneDomainBehavior());
        // Add more later:
        // DomainRegistry.register(new PrototypeDomainBehavior());
    }
}