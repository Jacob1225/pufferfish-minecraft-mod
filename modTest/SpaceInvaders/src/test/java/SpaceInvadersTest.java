import static org.junit.jupiter.api.Assertions.*;

class SpaceInvadersTest {
    int ALIEN_INIT_X = 150;//Position of first invader
    int ALIEN_INIT_Y = 5;
    int NumberOfInvaders=24;
    public static void main(String[] args){
//NOT ORIGINAL CODE -> creating individual invaders
        aliens = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++) {

                var alien = new SpaceInvaders(ALIEN_INIT_X + 18 * j, ALIEN_INIT_Y + 18 * i);
                aliens.add(alien);
            }
        }
    }

    public static void invaderShotTest()
    {
        System.out.print(SpaceInvaders.invaderShot(3,4));
    }

    public static void InvaderMoveTest(){
        //send board limits
    }
}