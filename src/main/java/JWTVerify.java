import java.lang.annotation.*;

/**
 * Created by Administrator on 2017/8/6.
 */
@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface JWTVerify {
    boolean TimeoutCheck() default false;

    boolean RoleCheck() default false;
}
