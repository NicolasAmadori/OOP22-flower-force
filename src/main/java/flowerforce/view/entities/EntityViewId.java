package flowerforce.view.entities;

/**
 *
 */
public enum EntityViewId {
    /**
     *
     */
    SUNFLOWER("sunflower.gif"),

    /**
     *
     */
    PEASHOOTER("peashooter.gif"),

    /**
     *
     */
   SNOWSHOOTER("snowshooter.gif"),

    /**
     *
     */
   FASTSHOOTER("fastshooter.gif"),

    /**
     *
     */
   FIRESHOOTER("fireshooter.gif");

   private final String imageName;

   private EntityViewId(final String imageName) {
       this.imageName = imageName;
   }

   public String getImageName() {
       return this.imageName;
   }

}
