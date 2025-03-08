abstract class Bird {
    protected String name;
    public Bird(String n){
        name = n;
    }

    public void call() {
        System.out.println(name + " chirps!");
    }
}


interface NestBuilding {
    public void nest();
}

class Chicken extends Bird {
    public Chicken(String n){
        super(n);
    }
    public void call(){
        System.out.println(name + " clucks!");
    }
}

class Robin extends Bird implements NestBuilding {
    public Robin(String n){
        super(n);
    }

    public void nest(){
        System.out.println("The robin builds a nest from small sticks.");
    }
}

class Eagle extends Bird implements NestBuilding {
    public Eagle(String n){
        super(n);
    }

    public void call(){
        System.out.println(name + " screeches!");
    }

    public void nest(){
        System.out.println("The eagle builds a nest from large sticks.");
    }
}

class Flock {
    ArrayList<Bird> birds;

    public Flock(Bird bird){
        birds.add(bird);
    }

    public Flock(){
        this(new Chicken("Alex"));
    }
}