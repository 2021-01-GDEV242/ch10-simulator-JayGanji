import java.util.List;
import java.util.Random;

/**
 * A class representing shared characteristics of animals.
 * 
 * @author David J. Barnes and Michael Kölling modified by Jay Ganji
 * @version 2021.04.12 (3)
 */
public abstract class Animal
{
    // Whether the animal is alive or not.
    private boolean alive;
    // The animal's field.
    private Field field;
    // The animal's position in the field.
    private Location location;
    // Animal age
    protected int age;
    private static final Random rand = Randomizer.getRandom();
    
    /**
     * Create a new animal at location in field.
     * 
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;
        setLocation(location);
        age = 0;
    }
    
    /**
     * Make this animal act - that is: make it do
     * whatever it wants/needs to do.
     * @param newAnimals A list to receive newly born animals.
     */
    abstract public void act(List<Animal> newAnimals);

    /**
     * Check whether the animal is alive or not.
     * @return true if the animal is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the animal is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the animal's location.
     * @return The animal's location.
     */
    protected Location getLocation()
    {
        return location;
    }
    
    /**
     * Place the animal at the new location in the given field.
     * @param newLocation The animal's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }
    
    /**
     * Return the animal's field.
     * @return The animal's field.
     */
    protected Field getField()
    {
        return field;
    }
    
    /**
     * Set the animal age
     * @param age The animal's new age
     */
    protected void setAge(int age)
    {
        this.age = age;
    }
    
    /**
     * Return the animal's age.
     * @return The animal's age.
     */
    protected int getAge()
    {
        return age;
    }
    
    /**
     * An animal can breed if it has reached the breeding age.
     */
    public boolean canBreed()
    {
        return age >= getBreedingAge();
    }
    
    /**
     * Return the breeding age of this animal.
     * @return The breeding age of this animal.
     */
    abstract protected int getBreedingAge();
    
    /**
     * Return the max age of this animal.
     * @return The max age of this animal.
     */
    abstract protected int getMaxAge();
    
    /**
     * Increase the age. This could result in the animal's death.
     */
    public void incrementAge()
    {
        age++;
        if(age > getMaxAge()) {
            setDead();
        }
    }
    
    /**
     * Generate a number representing the number of births,
     * if it can breed.
     * @return The number of births (may be zero).
     */
    public int breed()
    {
        int births = 0;
        if(canBreed() && rand.nextDouble() <= getBreedingProb()) {
            births = rand.nextInt(getMaxLitterSize()) + 1;
        }
        return births;
    }
    
    /**
     * Return the breding probability of this animal.
     * @return The breeding probability of this animal.
     */
    abstract protected double getBreedingProb();
    
    /**
     * Return the max litter size of this animal.
     * @return The max litter size of this animal.
     */
    abstract protected int getMaxLitterSize();
}
