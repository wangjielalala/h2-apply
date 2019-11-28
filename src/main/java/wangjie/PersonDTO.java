package wangjie;

import javax.json.bind.annotation.*;

/**
 * @author Sai
 * Created by Sai on 2019-03-15.
 * @see JsonbProperty - 用于指定自定义字段名称
 * @see JsonbTransient - 当我们想要在反序列化/序列化期间忽略该字段时
 * @see JsonbDateFormat - 当我们想要定义日期的显示格式时
 * @see JsonbNumberFormat - 用于指定数值的显示格式
 * @see JsonbNillable - 用于启用空值的序列化
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PersonDTO {

    // 工号
    @JsonbProperty
    private String name;

    // 年龄
    @JsonbProperty
    private int age;

    // 为了使用jsonb,必须有一个空的默认构造方法
    public PersonDTO() {

    }

    public PersonDTO( String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "PersonDTO{" +
                ", name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

}