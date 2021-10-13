import javafx.util.converter.LocalDateStringConverter;
import lombok.Data;
import sun.util.resources.LocaleData;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class UserService {
    @Data
    static class UserInfo {        //@TableId(value = "id", type = IdType.AUTO)
        private Integer id;        //@ApiModelProperty(value = "用户id"
        private String userId;        //@ApiModelProperty(value = "用户昵称")
        private String userName;        //@ApiModelProperty(value = "正式使用的图片，数组格式")
        private String img;        //@ApiModelProperty(value = "1-男 2-女")
        private Integer gender;        //@ApiModelProperty(value = "生日")
        private LocalDate birthday;        //@ApiModelProperty(value = "身高-cm")
        private Integer height;        //@ApiModelProperty(value = "家乡，如：浙江省-杭州市-西湖区")
        private String hometown;
    }

    /**
     * 组装参数，返回
     */
    public static UserInfo buildUserInfo(Map userInfoMap) {
        UserInfo userInfo = new UserInfo();
        /** 请补充代码，将userInfoMap传入的参数，转化为userInfo*/
        /** 注意：此处传入的参数可能为任意的其中一个，非固定为birthday哦，
         * 原则上前端每次只会传入一个用户信息参数，但若传入多个，后端也只会保存其中一个参数*/

        Set<String> set = userInfoMap.keySet();
        for (String str: set){
            switch (str){
                case "id":userInfo.setId((Integer) userInfoMap.get(str));break;
                case "userId":userInfo.setUserId((String) userInfoMap.get(str));break;
                case "userName":userInfo.setUserName((String) userInfoMap.get(str));break;
                case "img":userInfo.setImg((String) userInfoMap.get(str));break;
                case "gender":userInfo.setGender((Integer) userInfoMap.get(str));break;
                case "birthday": {
                    DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate ld = LocalDate.parse((String)userInfoMap.get(str), fmt);
                    userInfo.setBirthday(ld );
                    break;
                }
                case "height":userInfo.setHeight((Integer) userInfoMap.get(str));break;
                case "hometown":userInfo.setHometown((String) userInfoMap.get(str));break;
            }
        }


        return userInfo;
    }

    public static void main(String[] args) throws Exception {
        Map<String,String> map = new HashMap<>();
        map.put("birthday", "1999-01-01");
        map.put("userName","用户名XXX");
        System.out.println(buildUserInfo(map));
    }
}
