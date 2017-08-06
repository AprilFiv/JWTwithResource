import com.auth0.jwt.algorithms.Algorithm;

/**
 * Created by Administrator on 2017/8/6.
 */
public interface BaseAlgorithm {
    public void init();

    public Algorithm getAlgorithm();
}
