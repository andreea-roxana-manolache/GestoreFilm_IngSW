package gestorefilm.memento;

public interface Originator {
    Memento creaMemento();
    void ripristinaDaMemento(Memento memento);
}
