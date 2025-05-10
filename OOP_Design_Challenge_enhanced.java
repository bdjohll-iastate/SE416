abstract class Bird {
    protected String name;                //name of individual bird
    protected String callVerb = "chirps"; //verb describing bird call
    public Bird(String n){
        name = n;
    }

    public void call() {
        System.out.println(name + " " + callVerb + "!");
    }
}


interface NestBuilding {
    public void nest(); //Print a message describing the animal's nest.
}

class Chicken extends Bird {
    protected String callVerb = "clucks";
    public Chicken(String n){
        super(n);
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
    protected String callVerb = "screeches";
    public Eagle(String n){
        super(n);
    }

    public void nest(){
        System.out.println("The eagle builds a nest from large sticks.");
    }
}

class Flock {
    List<Bird> birds;

    public Flock(Bird bird){
        birds = new ArrayList<Bird>();
        birds.add(bird);
    }

    public Flock(){
        this(new Chicken("Alex"));
    }

    public addBird(Bird bird){
        birds.add(bird);
    }
}