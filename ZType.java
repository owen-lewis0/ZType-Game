import tester.*;                // The tester library
import javalib.worldimages.*;   // images, like RectangleImage or OverlayImages
import javalib.funworld.*;      // the abstract World class and the big-bang library
import java.awt.Color;          // general colors (as triples of red,green,blue values)
import java.util.Random;        // and predefined colors (Red, Green, Yellow, Blue, Black, White)

//Represents a word
interface IWord {
  
  //Returns true if the first letter of the word matches 's'
  boolean matches(String s);
  
  //Removes the first letter from the word
  IWord reduce();
  
  //Returns whether this IWord is an empty string
  boolean isEmpty();
  
  //Draws this word in it's place on 'scene'
  WorldScene drawWord(WorldScene scene);
  
  //Moves the word toward the bottom of the screen on each tick
  IWord moveWord();
  
  //To check if this word causes the game to end
  boolean touchesBottom(int height);
  
  //Either converts an IWord to active, or returns the already active word
  IWord makeActive();
  
  //Returns active status of an IWord
  boolean isActive();
}

//Represents an inactiveword with a string, x coordinate, and y coordinate
class InactiveWord implements IWord {
  /**
   * FIELDS:
   * word -> String
   * x -> int
   * y -> int
   * METHODS:
   * matches(String) -> boolean
   * reduce() -> IWord
   * isEmpty() -> boolean
   * drawWord(WorldScene) -> WorldScene
   * moveWord() -> IWord
   * touchesBottom(int) -> boolean
   * makeActive() -> IWord
   * isActive() -> boolean
   * METHODS ON FIELDS:
   * - none -
   */
  
  String word;
  int x;
  int y;
  
  InactiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }
  
  //Checks if first character of a word matches the given character
  public boolean matches(String s) {
    return (!word.equals("") && word.substring(0, 1).equals(s));
  }
  
  //Creates a duplicate IWord with the first letter removed
  public IWord reduce() {
    return new InactiveWord(this.word, x, y);
  }
  
  //Returns whether or not this.word is an empty string
  public boolean isEmpty() {
    return (this.word.equals(""));
  }
  
  //Draws this word at its designated x and y coordinate on the given 'scene'
  public WorldScene drawWord(WorldScene scene) {
    return scene.placeImageXY(new TextImage(this.word, 35, Color.RED), this.x, this.y);
  }
  
  //Moves the word 25 pixels towards the bottom of the screen
  public IWord moveWord() {
    return new InactiveWord(this.word, this.x, this.y + 25);
  }
  
  //Returns true if this word has passed the given height
  public boolean touchesBottom(int height) {
    return this.y >= height;
  }
  
  //Returns an ActiveWord version of this word
  public IWord makeActive() {
    return new ActiveWord(this.word, this.x, this.y);
  }
  
  //Tells that this is NOT active
  public boolean isActive() {
    return false;
  }
}

//Represents an activeword with a string word, x coordinate, and y coordinate
class ActiveWord implements IWord {
  /**
   * FIELDS:
   * word -> String
   * x -> int
   * y -> int
   * METHODS:
   * matches(String) -> boolean
   * reduce() -> IWord
   * isEmpty() -> boolean
   * drawWord(WorldScene) -> WorldScene
   * moveWord() -> IWord
   * touchesBottom(int) -> boolean
   * makeActive() -> IWord
   * isActive() -> boolean
   * METHODS ON FIELDS:
   * - none -
   */
  
  String word;
  int x;
  int y;
  
  ActiveWord(String word, int x, int y) {
    this.word = word;
    this.x = x;
    this.y = y;
  }
  
  //Checks if first character of a word matches the given character
  public boolean matches(String s) {
    return (!word.equals("") && word.substring(0, 1).equals(s));
  }
  
  //Creates a duplicate IWord with the first letter removed
  public IWord reduce() {
    return new ActiveWord(word.substring(1), x, y);
  }
  
  //Returns whether or not this.word is an empty string
  public boolean isEmpty() {
    return this.word.equals("");
  }
  
  //Draws this word at its designated x and y coordinate on the given 'scene'
  public WorldScene drawWord(WorldScene scene) {
    return scene.placeImageXY(new TextImage(this.word, 35, Color.GREEN), this.x, this.y);
  }
  
  //Moves the word 25 pixels towards the bottom of the screen
  public IWord moveWord() {
    return new ActiveWord(this.word, this.x, this.y + 25);
  }
  
  //Returns true if this word has passed the given height
  public boolean touchesBottom(int height) {
    return this.y >= height;
  }
  
  //Returns an ActiveWord version of this word
  public IWord makeActive() {
    return this;
  }
  
  //Tells that this word IS active
  public boolean isActive() {
    return true;
  }
}

//Represents a list of words
interface ILoWord {
  //Removes the first letter from an active word in the list that starts with 'letter'
  ILoWord checkAndReduce(String letter);
  
  //Adds the given IWord 'word' to the end of the list
  ILoWord addToEnd(IWord word);
  
  //Gets rid of any empty-string words in the list
  ILoWord filterOutEmpties();
  
  //Moves all words towards the bottom of the screen
  ILoWord move();
  
  //Draws all the words in the list in their specific locations
  WorldScene draw(WorldScene scene);
  
  //Checks if any of the words have reached the bottom of the screen
  boolean touchBottom(int height);
  
  //Finds first occurrence of a word starting with 'letter' and makes it active
  ILoWord activateWord(String letter);
  
  //Checks if there are any active words in the list
  boolean hasActive();
  
}

//Represents an empty list of words
class MtLoWord implements ILoWord {
  /**
   * FIELDS:
   * - none -
   * METHODS:
   * - checkAndReduce(String) -> ILoWord
   * - addToEnd(IWord) -> ILoWord
   * - filterOutEmpties() -> ILoWord
   * - draw(WorldScene) -> WorldScene
   * - move() -> ILoWord
   * - touchBottom(int) -> boolean
   * - activateWord(String) -> ILoWord
   * - hasActive() -> boolean
   * METHODS ON FIELDS:
   * - none -
   */
  
  //Returns empty list as there is nothing to check
  public ILoWord checkAndReduce(String letter) {
    return this;
  }
  
  //Adds a word to the end of a list of words
  public ILoWord addToEnd(IWord word) {
    /* Fields:
     * word.word -- String
     * word.x -- int
     * word.y -- int
     * 
     * Methods:
     * word.matches(String) -- boolean
     * word.reduce() -- IWord
     * word.isEmpty() -- boolean
     * word.drawWord(WorldScene) -- WorldScene
     * word.moveWord() -- IWord
     * word.touchesBottom(int) -- boolean
     * word.makeActive() -- IWord
     * word.isActive() -- boolean
     * 
     * Methods for fields:
     * (none)
     * 
     */
    
    return new ConsLoWord(word, this);
  }
  
  //Returns an empty list as there is nothing to filter
  public ILoWord filterOutEmpties() {
    return this;
  }
  
  //Returns empty scene as no words to draw
  public WorldScene draw(WorldScene scene) {
    return scene;
  }
  
  //Returns empty list as not words to move
  public ILoWord move() {
    return this;
  }
  
  //Returns false as there is no words touching bottom of scene
  public boolean touchBottom(int height) {
    return false;
  }
  
  //Returns empty list as there is no words to activate
  public ILoWord activateWord(String letter) {
    return this;
  }
  
  //Returns false as there is no active words
  public boolean hasActive() {
    return false;
  }
}

//Represents a list of words
class ConsLoWord implements ILoWord {
  /**
   * FIELDS:
   * - first -> IWord
   * - rest -> ILoWord
   * METHODS:
   * - checkAndReduce(String) -> ILoWord
   * - addToEnd(IWord) -> ILoWord
   * - filterOutEmpties() -> ILoWord
   * - draw(WorldScene) -> WorldScene
   * - move() -> ILoWord
   * - touchBottom(int) -> boolean
   * - activateWord(String) -> ILoWord
   * - hasActive() -> boolean
   * METHODS ON FIELDS:
   * - first.reduce() -> IWord
   * - first.drawWord(WorldScene) -> WorldScene
   * - first.moveWord() -> IWord
   * - first.touchesBottom(int) -> boolean
   * - first.matches(String) -> boolean
   * - first.makeActive() -> IWord
   * - first.isActive() -> boolean
   */
  
  IWord first;
  ILoWord rest;
  
  ConsLoWord(IWord first, ILoWord rest) {
    this.first = first;
    this.rest = rest;
  }
  
  //Removes first letter of string in list if it matches given string
  public ILoWord checkAndReduce(String letter) {
    if (this.first.matches(letter)) {
      return new ConsLoWord(this.first.reduce(), this.rest.checkAndReduce(letter));
    }
    else {
      return new ConsLoWord(this.first, this.rest.checkAndReduce(letter));
    }
  }
  
  //Adds a word to the end of list
  public ILoWord addToEnd(IWord word) {
    /* Fields:
     * word.word -- String
     * word.x -- int
     * word.y -- int
     * 
     * Methods:
     * word.matches(String) -- boolean
     * word.reduce() -- IWord
     * word.isEmpty() -- boolean
     * word.drawWord(WorldScene) -- WorldScene
     * word.moveWord() -- IWord
     * word.touchesBottom(int) -- boolean
     * word.makeActive() -- IWord
     * word.isActive() -- boolean
     * 
     * Methods for fields:
     * (none)
     * 
     */

    return new ConsLoWord(this.first, this.rest.addToEnd(word));
  }
  
  //Filters out words with empty strings
  public ILoWord filterOutEmpties() {
    if (this.first.isEmpty()) {
      return this.rest.filterOutEmpties();
    }
    else {
      return new ConsLoWord(this.first, this.rest.filterOutEmpties());
    }
  }
  
  //Draws list of words to a scene
  public WorldScene draw(WorldScene scene) {
    return this.rest.draw(this.first.drawWord(scene));
  }
  
  //Moves the words in the list down every tick
  public ILoWord move() {
    return new ConsLoWord(this.first.moveWord(), this.rest.move());
  }
  
  //Does the word touch the bottom of the screen?
  public boolean touchBottom(int height) {
    return this.first.touchesBottom(height) || this.rest.touchBottom(height);
  }
  
  //Turns inactive words into active words if first letter
  //of string matches given string
  public ILoWord activateWord(String letter) {
    if (this.first.matches(letter) && !this.hasActive()) {
      return new ConsLoWord(this.first.makeActive(), this.rest);
    }
    return new ConsLoWord(this.first, this.rest.activateWord(letter));
  }
  
  //Does this list have active words?
  public boolean hasActive() {
    return this.first.isActive() || this.rest.hasActive();
  }
}

//Represents a static class that produces random words of length 6
class Utils {
  /**
   * FIELDS:
   * - rand -> Random
   * METHODS:
   * - makeWord(String, int) -> String
   * METHODS ON FIELDS:
   * - rand.NextInt(int) -> int
   */
  
  Random rand;
  
  Utils(Random rand) {
    this.rand = rand;
  }
  
  //Makes a new string starting with 'acc' (should be an empty string for initial call)
  //String is 'letters' characters long, all randomly selected from String alph
  String makeWord(String acc, int letters) {
    
    String alph = "abcdefghijklmnopqrstuvwxyz";
    
    int randomIndex = rand.nextInt(alph.length());
    String randomLetter = alph.substring(randomIndex, randomIndex + 1);
    
    if (letters > 0) {
      return makeWord(acc + randomLetter, letters - 1);
    } else {
      return acc;
    }
  }
}

//Represents a ZTypeWorld game
class ZTypeWorld extends World {
  /**
   * FIELDS:
   * - this.words -> ILoWords
   * - this.rand -> Random
   * METHODS:
   * - this.makeScene() -> WorldScene
   * - this.onTick() -> World
   * - this.onKeyEvent() -> World
   * - this.lastScene(String) -> WorldScene
   * METHODS ON FIELDS:
   * - words.draw(WorldScene) -> WorldScene
   * - words.endOfWorld(String) -> World
   * - words.addToEnd(IWord) -> ILoWord
   * - words.move() -> ILoWord
   * - words.checkAndReduce(String) -> ILoWord
   */
  
  ILoWord words;
  Random rand = new Random();
  final int WIDTH = 600;
  final int HEIGHT = 900;
  final int TICKRATE = 1;
  final int WORDLIMIT = 6;
  
  ZTypeWorld(ILoWord words) {
    this.words = words;
  }
  
  ZTypeWorld(ILoWord words, Random rand) {
    this.words = words;
    this.rand = rand;
  }
  
  Utils u = new Utils(rand);
  
  //Sets up initial World program
  public WorldScene makeScene() {
    return this.words.draw(new WorldScene(WIDTH, HEIGHT));
  }
  
  //Moves word down the screen and produces a new word every tick
  //Produces a game over screen if word touches bottom
  public World onTick() {
    if (this.words.touchBottom(600)) {
      return this.endOfWorld("Game Over");
    } else {
      ILoWord movedWords = this.words.addToEnd(new InactiveWord(
          u.makeWord("", u.rand.nextInt(6) + 3),
          (u.rand.nextInt(WIDTH - 110) + 60), 10)).filterOutEmpties();
      return new ZTypeWorld(movedWords.move());
    }
  }
  
  //Moves word down the screen and produces a new word every tick
  //Produces a game over screen if word touches bottom
  //Uses a seeded random object for making the new word make it easier for testing
  public World onTickForTesting() {
    if (this.words.touchBottom(600)) {
      return this.endOfWorld("Game Over");
    } else {
      Random seededRand = new Random(20);
      Utils utilsForTesting = new Utils(seededRand);
      ILoWord movedWords = this.words.addToEnd(new InactiveWord(
          utilsForTesting.makeWord("", utilsForTesting.rand.nextInt(6)),
          (utilsForTesting.rand.nextInt(WIDTH - 110) + 60), 10)).filterOutEmpties();
      return new ZTypeWorld(movedWords.move(), seededRand);
    }
  }
  
  //Handles all the key presses for the ZType game
  public World onKeyEvent(String key) {
    
    if (this.words.hasActive()) {
      ILoWord updated = this.words.checkAndReduce(key);
      return new ZTypeWorld(updated);
    } 
    return new ZTypeWorld(this.words.activateWord(key).checkAndReduce(key));
  }
  
  //Shows the game over scene when a word hits the bottom of the screen
  public WorldScene lastScene(String msg) {
    WorldScene scene = new WorldScene(WIDTH, HEIGHT);
    scene = scene.placeImageXY(new TextImage(msg, 35, Color.BLUE), WIDTH / 2 , HEIGHT / 2);
    return scene;
  }
}

// Examples and Tests for the ZTypeWorld class
class ExamplesZType {

  IWord hi = new ActiveWord("hi", 15, 15);
  IWord ray = new ActiveWord("ray", 10, 12);
  IWord no = new InactiveWord("no", 25, 25);
  IWord yes = new InactiveWord("yes", 30, 30);
  IWord emptyString = new InactiveWord("", 10, 10);
  IWord aWord1 = new ActiveWord("Hello", 10, 5);
  IWord aWord2 = new ActiveWord("World", 1, 400);
  IWord iWord1 = new InactiveWord("Hi", 11, 6);
  IWord iWord2 = new InactiveWord("Earth", 0, 399);
  
  ILoWord actives = new ConsLoWord(aWord1,
      new ConsLoWord(aWord2, new MtLoWord()));
  ILoWord inactives = new ConsLoWord(iWord1,
      new ConsLoWord(iWord2, new MtLoWord()));
  ILoWord words = new ConsLoWord(aWord1,
      new ConsLoWord(aWord2,
          new ConsLoWord(iWord1,
              new ConsLoWord(iWord2,
                  new MtLoWord()))));
  ILoWord mt = new MtLoWord();
  ILoWord emptyList = new MtLoWord();
  ILoWord list1 = new ConsLoWord(this.hi, this.emptyList);
  ILoWord list2 = new ConsLoWord(this.ray, this.list1);
  ILoWord list3 = new ConsLoWord(this.no, this.list2);
  ILoWord emptyStringList = new ConsLoWord(this.emptyString, this.list2);
  
  Utils u = new Utils(new Random());
  
  WorldScene emptyScene = new WorldScene(400, 300);
  
  ZTypeWorld dw = new ZTypeWorld(new ConsLoWord(new InactiveWord("svphp", 255, 35),
      new MtLoWord()), new Random(20));
  ZTypeWorld init = new ZTypeWorld(this.mt, new Random(20));
  
  //Tests for matches method
  boolean testMatches(Tester t) {
    return t.checkExpect(this.ray.matches("r"), true)
        && t.checkExpect(this.ray.matches("s"), false)
        && t.checkExpect(this.ray.matches(""), false);
  }
  
  //Tests for reduce method
  boolean testReduce(Tester t) {
    return t.checkExpect(this.ray.reduce(), new ActiveWord("ay", 10, 12))
        && t.checkExpect(this.iWord1.reduce(), this.iWord1)
        && t.checkExpect(this.emptyString.reduce(), this.emptyString);
  }
  
  //Tests for isEmpty method
  boolean testisEmpty(Tester t) {
    return t.checkExpect(this.ray.isEmpty(), false)
        && t.checkExpect(this.iWord1.isEmpty(), false)
        && t.checkExpect(this.emptyString.isEmpty(), true);
  }
  
  // Tests draw method
  boolean testdrawWord(Tester t) {
    // Tests for drawing emptyList
    return t.checkExpect(emptyList.draw(emptyScene), emptyScene)
        // Tests for drawing a single word list
        && t.checkExpect(list1.draw(emptyScene), 
            emptyScene.placeImageXY(new TextImage("hi", 35, Color.GREEN), 15, 15))
        // Tests for drawing multi word list
        && t.checkExpect(list2.draw(emptyScene), 
            emptyScene.placeImageXY(new TextImage("ray", 35, Color.GREEN), 10, 12)
                      .placeImageXY(new TextImage("hi", 35, Color.GREEN), 15, 15));
  }
  
  //Tests for moveWord method
  boolean testMoveWord(Tester t) {
    return t.checkExpect(this.ray.moveWord(), new ActiveWord("ray", 10, 37))
        && t.checkExpect(this.iWord1.moveWord(), new InactiveWord("Hi", 11, 31))
        && t.checkExpect(this.emptyString.moveWord(), new InactiveWord("", 10, 35));
  }
  
  //Tests for touchesBottom
  boolean testTouchesBottom(Tester t) {
    return t.checkExpect(this.ray.touchesBottom(100), false)
        && t.checkExpect(this.ray.touchesBottom(12), true)
        && t.checkExpect(this.emptyString.touchesBottom(100), false);
  }
  
  //Tests for makeActive method
  boolean testMakeActive(Tester t) {
    return t.checkExpect(this.ray.makeActive(), this.ray)
        && t.checkExpect(this.iWord1.makeActive(), new ActiveWord("Hi", 11, 6))
        && t.checkExpect(this.emptyString.makeActive(), new ActiveWord("", 10 ,10));
  }
  
  //Tests for isActive method
  boolean testisActive(Tester t) {
    return t.checkExpect(this.ray.isActive(), true)
        && t.checkExpect(this.iWord1.isActive(), false)
        && t.checkExpect(this.emptyString.isActive(), false);
  }
  
  //Tests for checkAndReduce method
  boolean testCheckAndReduce(Tester t) {
    return t.checkExpect(words.checkAndReduce("H"),
        //Case for when the function changes the list
        new ConsLoWord(new ActiveWord("ello", 10, 5),
            new ConsLoWord(aWord2,
                new ConsLoWord(iWord1,
                    new ConsLoWord(iWord2,
                        new MtLoWord())))))
        //Case for empty list
        && t.checkExpect(mt.checkAndReduce("Doesn't matter"),
            new MtLoWord())
        //Case for list with all inactive words
        && t.checkExpect(inactives.checkAndReduce("h"),
            inactives);
  }

  //Tests for addToEnd method
  boolean testAddToEnd(Tester t) {
    return t.checkExpect(words.addToEnd(new ActiveWord("Word", 3, 4)),
        //Case for adding a word to an existing list
        new ConsLoWord(aWord1,
            new ConsLoWord(aWord2,
                new ConsLoWord(iWord1,
                    new ConsLoWord(iWord2,
                        new ConsLoWord(new ActiveWord("Word", 3, 4),
                            new MtLoWord()))))))
        //Case for adding a word to an empty list
        && t.checkExpect(mt.addToEnd(aWord1),
            new ConsLoWord(aWord1, new MtLoWord()));
  }
  
  ILoWord emptyStrs = new ConsLoWord(new ActiveWord("Hello", 10, 5),
      new ConsLoWord(new ActiveWord("", 3, 1),
          new ConsLoWord(new ActiveWord("World", 1, 400),
              new MtLoWord())));
  
  //Tests for filterOutEmpties method
  boolean testFilterOutEmpties(Tester t) {
    return t.checkExpect(mt.filterOutEmpties(),
        //Test case for list with no empty strings
        new MtLoWord())
        //Test case for empty list
        && t.checkExpect(words.filterOutEmpties(),
            words)
        //Test case for list WITH empty strings
        && t.checkExpect(emptyStrs.filterOutEmpties(),
            new ConsLoWord(new ActiveWord("Hello", 10, 5),
                new ConsLoWord(new ActiveWord("World", 1, 400),
                    new MtLoWord())));
  }
  
  //Tests for move method
  boolean testMove(Tester t) {
    return t.checkExpect(this.list2.move(), 
        new ConsLoWord(new ActiveWord("ray", 10, 37),
            new ConsLoWord(new ActiveWord("hi", 15, 40), this.mt)))
        && t.checkExpect(this.inactives.move(), new ConsLoWord(new InactiveWord("Hi", 11, 31),
            new ConsLoWord(new InactiveWord("Earth", 0, 424), this.mt)))
        && t.checkExpect(this.emptyList.move(), this.emptyList);
  }
  
  //Tests for touchBottom method
  boolean testTouchBottom(Tester t) {
    return t.checkExpect(this.list2.touchBottom(600), false)
        && t.checkExpect(this.inactives.touchBottom(100), true)
        && t.checkExpect(this.emptyList.touchBottom(100), false);
  }
  
  //Tests for activateWord method
  boolean testActivateWord(Tester t) {
    return t.checkExpect(this.inactives.activateWord("H"), 
        new ConsLoWord(new ActiveWord("Hi", 11, 6), 
            new ConsLoWord(new InactiveWord("Earth", 0, 399), this.mt)))
        && t.checkExpect(this.inactives.activateWord("m"), this.inactives)
        && t.checkExpect(this.emptyList.activateWord("m"), this.emptyList)
        && t.checkExpect(this.actives.activateWord("s"), this.actives);
  }

  //Tests for hasActive method
  boolean testHaveActive(Tester t) {
    return t.checkExpect(this.list2.hasActive(), true)
        && t.checkExpect(this.inactives.hasActive(), false)
        && t.checkExpect(this.emptyList.hasActive(), false);
  }
  
  //Tests for makeWord method
  boolean testMakeWord(Tester t) {
    Utils u1 = new Utils(new Random(100));
    return t.checkExpect(u1.makeWord("", 6), "zuakvq")
        && t.checkExpect(u1.makeWord("", 5), "kdbyp")
        && t.checkExpect(u1.makeWord("", 0), "");
  }
  
  //Tests onTick method
  boolean testOnTickWithSeededRandom(Tester t) {
    return t.checkExpect(init.onTickForTesting(), dw);
  }
  
  //Tests for BigBang to run game
  boolean testBigBang(Tester t) {
    ZTypeWorld world = new ZTypeWorld(new MtLoWord());
    int WIDTH = 600;
    int HEIGHT = 900;
    double tickRate = 2;
    return world.bigBang(WIDTH, HEIGHT, tickRate);
  }
}