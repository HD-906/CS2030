ImList<Service> serveCruises(ImList<Cruise> cruises) {
    ImList<Service> services = new ImList<>();

    for (int i = 0; i < cruises.size(); i++) {
        Cruise cruise = cruises.get(i);
        Loader loader = new Loader(i);
        services = services.add(new Service(loader, cruise));
    }
    
    return services;
}