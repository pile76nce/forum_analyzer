package rs.etf.analyzer.test.post;

import rs.etf.analyzer.parser.NoiseWords;
import java.io.File;
import rs.etf.analyzer.post.PostObject;
import java.util.ArrayList;
import rs.etf.analyzer.post.Post;
import java.io.FileNotFoundException;
import rs.etf.analyzer.test.BaseTestCase;

/**
 *
 * @author Dejan Prodanović
 * @version 1.0
 */
public class PostObjectTest extends BaseTestCase
{
  public void testOne()
  {
    try
    {
//      sve reci sa velikom frekvencijom
      NoiseWords.getAllNoiseWords();

      ArrayList<Post> loPostList = new ArrayList<Post>();

      loPostList.add(new PostObject(new File("./test_data/post_cache_11.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_12.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_13.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_14.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_15.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_16.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_17.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_18.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_19.txt")));

      loPostList.add(new PostObject(new File("./test_data/post_cache_21.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_22.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_23.txt")));
      loPostList.add(new PostObject(new File("./test_data/post_cache_24.txt")));

//      loPostList.add(new AbstractPostObject(new File("./test_data/HW8 4Way Set Associative.txt")));
//      loPostList.add(new AbstractPostObject(new File("./test_data/test.txt")));

      float ldCurrent;
      float ldBest = 0;

      int liBestIndexI = 0;
      int liBestIndexJ = 0;

      Post loTempPost;
      for (int i = 0; i < loPostList.size() - 1; i++)
      {
        loTempPost = loPostList.get(i);
        for (int j = i+1; j < loPostList.size(); j++)
        {
          ldCurrent = loTempPost.compareTo(loPostList.get(j));
          if (ldCurrent > ldBest)
          {
            ldBest = ldCurrent;
            liBestIndexI = i;
            liBestIndexJ = j;

            System.out.println(String.format("%f; %d, %d", ldBest, liBestIndexI, liBestIndexJ));
          }
        }
      }

      System.out.println();
      System.out.println(String.format("%f; %d, %d", ldBest, liBestIndexI, liBestIndexJ));

//      System.out.println(String.format("%f", loPostList.get(3).compareTo(loPostList.get(4))));
//      System.out.println(String.format("%f", loPostList.get(1).compareTo(loPostList.get(2))));
//      System.out.println(String.format("%f", loPostList.get(1).compareTo(loPostList.get(2))));
    }
    catch(FileNotFoundException e)
    {
      e.printStackTrace();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
}
