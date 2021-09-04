package hello.login.web.argumentresolver;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// parameter에 사용할 애너테이션임을
@Target(ElementType.PARAMETER)
// 실제 동작할 때까지 애너테이션이 남아있어야 함
@Retention(RetentionPolicy.RUNTIME)
public @interface Login {
}
