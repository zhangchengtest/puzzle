package com.elephant;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.slf4j.helpers.MessageFormatter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SqlController {
    public static void main(String[] args) {

        InputStream in = SqlController.class.getResourceAsStream("/menu.sql");
        String roldId = "1108710239520230602";
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line;
            List<Menu> menuList = new ArrayList<>();
            while ((line = reader.readLine()) != null) {
//                System.out.println(line);
                if(StringUtils.isNotBlank(line)){


                    Pattern pattern = Pattern.compile("'(.*?)'");
                    Matcher matcher = pattern.matcher(line);

                    List<String> values = new ArrayList<>();
                    while (matcher.find()) {
                        values.add(matcher.group(1));
                    }

                    Menu menu = new Menu(
                            values.get(0),
                            values.get(2),
                            values.get(3),
                            values.get(4),
                            values.get(5),
                            values.get(6),
                            values.get(1)
                    );



                    menuList.add(menu);
                }
            }

            read(menuList,roldId);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void read( List<Menu> menuList, String roleId) {

        for(Menu mymenu: menuList) {

            String id = mymenu.getId();
            String pid = mymenu.getPid();
            String code = mymenu.getCode();
            String name = mymenu.getName();
            String menuLabel = mymenu.getMenuLabel();
            String url = mymenu.getUrl();
            String applicationId = mymenu.getApplication_id();
            Object[] objects = new Object[]{id, pid, code, name, menuLabel, url, applicationId};
//            menu(objects);

        }

        System.out.println();
        System.out.println();
        for(Menu mymenu: menuList) {

            String id = mymenu.getId();
            String pid = mymenu.getPid();
            String code = mymenu.getCode();
            String name = mymenu.getName();
            String menuLabel = mymenu.getMenuLabel();
            String url = mymenu.getUrl();
            String applicationId = mymenu.getApplication_id();

            Object[] objects = new Object[]{id, name, applicationId};
            sys_permission_group(objects);


        }
        System.out.println();
        System.out.println();
        for(Menu mymenu: menuList) {

            String id = mymenu.getId();
            String pid = mymenu.getPid();
            String code = mymenu.getCode();
            String name = mymenu.getName();
            String menuLabel = mymenu.getMenuLabel();
            String url = mymenu.getUrl();
            String applicationId = mymenu.getApplication_id();

            String permission_group_id = id;
            String permission_id = id;

            Object[] objects = new Object[]{id, permission_group_id, permission_id};
            sys_permission_group_link_permission(objects);

        }
        System.out.println();
        System.out.println();
        for(Menu mymenu: menuList) {

            String id = mymenu.getId();
            String pid = mymenu.getPid();
            String code = mymenu.getCode();
            String name = mymenu.getName();
            String menuLabel = mymenu.getMenuLabel();
            String url = mymenu.getUrl();
            String applicationId = mymenu.getApplication_id();

            String permission_group_id = id;

            String role_id = roleId;
            permission_group_id = id;

            Object[] objects = new Object[]{id, role_id, permission_group_id};

            sys_role_bind_permission_group(objects);
        }
    }


    private static void sys_permission_group(Object[] objects){
        String sys_menu = "INSERT INTO usercenter.`sys_permission_group` (`id`, `p_id`, `name`, `application_id`, `remark`, \n" +
                "`status`, `del`, `create_user_code`, `create_date`, `update_user_code`, `update_date`) \n" +
                "VALUES ('{}', NULL, '{}', '{}', NULL, 1, 0, 'cheng', \n" +
                "'2021-04-26 10:10:55', NULL, NULL);";

        System.out.println(MessageFormatter.arrayFormat(sys_menu, objects).getMessage().replace("\n", ""));
    }

    private static void sys_permission_group_link_permission(Object[] objects){
        String sys_menu = "INSERT INTO usercenter.`sys_permission_group_link_permission\n" +
                "` (`id`, `permission_group_id`, `permission_id`, `permisstion_type`, `status`, `del`, \n" +
                "`create_user_code`, `create_date`, `update_user_code`, `update_date`) \n" +
                "VALUES ('{}', '{}', '{}', 1, 1, 0, NULL, NULL, NULL, NULL);";

        System.out.println(MessageFormatter.arrayFormat(sys_menu, objects).getMessage().replace("\n", ""));
    }
    private static void sys_role_bind_permission_group(Object[] objects){
        String sys_menu = "INSERT INTO usercenter.`sys_role_bind_permission_group` (`id`, `role_id`, `permission_group_id`, \n" +
                "`status`, `del`, `create_user_code`, `create_date`, `update_user_code`, `update_date`) \n" +
                "VALUES ('{}', '{}', '{}', 1, 0,\n" +
                " 'cheng', '2021-04-26 10:10:55', NULL, NULL);";

        System.out.println(MessageFormatter.arrayFormat(sys_menu, objects).getMessage().replace("\n", ""));
    }
    private static void menu(Object[] objects){
        String sys_menu = "INSERT INTO usercenter.`sys_menu` (`id`, `p_id`, `code`, `name`, `notify_type`, " +
                "`menu_label`, `url`, `is_relative_path`, `level`,\n" +
                "`path`, `seq`, `pict_path`, `open_mode`, `tip`, `application_id`, `remark`, `status`, `del`, " +
                "`create_user_code`, `create_date`,\n" +
                "`update_user_code`, `update_date`, `org_level`)\n" +
                " VALUES ('{}', '{}', '{}', '{}', NULL, '{}', '{}', 1, 0, '',\n" +
                " NULL, NULL, NULL, NULL, '{}', NULL, 1, 0, 'cheng', '2021-04-26 10:10:55',\n" +
                "  NULL, NULL, NULL);";

        System.out.println(MessageFormatter.arrayFormat(sys_menu, objects).getMessage().replace("\n", ""));
    }

}

class Menu {
    private String id;
    private String code;
    private String name;
    private String menuLabel;
    private String url;
    private String application_id;

    private String pid;

    public Menu(String id, String code, String name, String menuLabel, String url, String application_id, String pid) {
        this.id = id;
        this.code = code;
        this.name = name;
        this.menuLabel = menuLabel;
        this.url = url;
        this.application_id = application_id;
        this.pid = pid;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getApplication_id() {
        return application_id;
    }

    public void setApplication_id(String application_id) {
        this.application_id = application_id;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }
}
