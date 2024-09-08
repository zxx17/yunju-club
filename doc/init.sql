create table auth_permission
(
    id             bigint auto_increment
        primary key,
    `name`           varchar(64)   null comment '权限名称',
    parent_id      bigint        null comment '父id',
    type           tinyint       null comment '权限类型 0菜单 1操作',
    menu_url       varchar(255)  null comment '菜单路由',
    `status`         tinyint(2)    null comment '状态 0启用 1禁用',
    `show`         tinyint(2)    null comment '展示状态 0展示 1隐藏',
    icon           varchar(128)  null comment '图标',
    permission_key varchar(64)   null comment '权限唯一标识',
    created_by     varchar(32)   null comment '创建人',
    created_time   datetime      null comment '创建时间',
    update_by      varchar(32)   null comment '更新人',
    update_time    datetime      null comment '更新时间',
    is_deleted     int default 0 null comment '是否被删除 0为删除 1已删除'
)
    charset = utf8;

create table auth_role
(
    id           bigint auto_increment
        primary key,
    role_name    varchar(32)   null comment '角色名称',
    role_key     varchar(64)   null comment '角色唯一标识',
    created_by   varchar(32)   null comment '创建人',
    created_time datetime      null comment '创建时间',
    update_by    varchar(32)   null comment '更新人',
    update_time  datetime      null comment '更新时间',
    is_deleted   int default 0 null comment '是否被删除 0未删除 1已删除'
)
    charset = utf8;

create table auth_role_permission
(
    id            bigint auto_increment
        primary key,
    role_id       bigint        null comment '角色id',
    permission_id bigint        null comment '权限id',
    created_by    varchar(32)   null comment '创建人',
    created_time  datetime      null comment '创建时间',
    update_by     varchar(32)   null comment '更新人',
    update_time   datetime      null comment '更新时间',
    is_deleted    int default 0 null
)
    comment '角色权限关联表' charset = utf8;

create table auth_user
(
    id           bigint auto_increment comment '主键'
        primary key,
    user_name    varchar(32)   null comment '用户名称/账号',
    nick_name    varchar(32)   null comment '昵称',
    email        varchar(32)   null comment '邮箱',
    phone        varchar(32)   null comment '手机号',
    `password`     varchar(64)   null comment '密码',
    sex          tinyint(2)    null comment '性别',
    avatar       varchar(255)  null comment '头像',
    `status`       tinyint(2)    null comment '状态 0启用 1禁用',
    introduce    varchar(255)  null comment '个人介绍',
    ext_json     varchar(255)  null comment '特殊字段',
    created_by   varchar(32)   null comment '创建人',
    created_time datetime      null comment '创建时间',
    update_by    varchar(32)   null comment '更新人',
    update_time  datetime      null comment '更新时间',
    is_deleted   int default 0 null comment '是否被删除 0未删除 1已删除'
)
    comment '用户信息表' charset = utf8;

create table auth_user_role
(
    id           bigint auto_increment comment '主键'
        primary key,
    user_id      bigint        null comment '用户id',
    role_id      bigint        null comment '角色id',
    created_by   varchar(32)   null comment '创建人',
    created_time datetime      null comment '创建时间',
    update_by    varchar(32)   null comment '更新人',
    update_time  datetime      null comment '更新时间',
    is_deleted   int default 0 null
)
    comment '用户角色表' charset = utf8;

create table subject_brief
(
    id             int auto_increment
        primary key,
    subject_id     text          null comment '题目id',
    subject_answer varchar(2000) null comment '题目答案',
    create_by      varchar(32)   null comment '创建人',
    create_time    datetime      null comment '创建时间',
    update_by      varchar(32)   null comment '更新人',
    update_time    datetime      null comment '更新时间',
    is_deleted     int           null comment '是否删除 0未删除|1已删除'
)
    comment '简答题表';

create table subject_category
(
    id            int auto_increment
        primary key,
    category_name varchar(32) null comment '分类名称',
    category_type int         null comment '分类类型',
    image_url     varchar(64) null comment '分类图片url',
    parent_id     int         null comment '父级id',
    create_by     varchar(32) null comment '创建人',
    create_time   datetime    null comment '创建时间',
    update_by     varchar(32) null comment '更新人',
    update_time   datetime    null comment '更新时间',
    is_deleted    int         null comment '删除判断'
)
    comment '题目分类表';

create table subject_info
(
    id                int auto_increment comment '主键'
        primary key,
    subject_name      varchar(128) null comment '题目名称',
    subject_difficult int          null comment '题目难度',
    settle_name       varchar(32)  null comment '出题人姓名',
    subject_type      int          null comment '题目类型 1单选 2多选 3判断 4简答',
    subject_source    int          null comment '题目分值',
    subject_parse     varchar(512) null comment '题目解析',
    create_by         varchar(32)  null comment '创建人',
    create_time       datetime     null comment '创建时间',
    update_by         varchar(32)  null comment '更新人',
    update_time       datetime     null comment '更新时间',
    is_deleted        int          null comment '是否删除 0未删除 1yi''s'
)
    comment '题目信息表';

create table subject_judge
(
    id          int auto_increment
        primary key,
    subject_id  int         null comment '题目id',
    is_correct  int         null comment '是否正确',
    create_by   varchar(32) null comment '创建人',
    create_time datetime    null comment '创建时间',
    update_by   varchar(32) null comment '更新人',
    update_time datetime    null comment '更新时间',
    is_deleted  int         null comment '是否删除 0未删除|1已删除'
)
    comment '判断题表';

create table subject_label
(
    id          bigint auto_increment
        primary key,
    label_name  varchar(32) null comment '标签名称',
    category_id int         null comment '分类Id',
    sort_num    int         null comment '排序',
    create_by   varchar(32) null comment '创建人',
    create_time datetime    null comment '创建时间',
    update_by   varchar(32) null comment '更新人',
    update_time datetime    null comment '更新时间',
    is_deleted  int         null comment '是否删除 0未删除|1已删除'
)
    comment '题目标签表';

create table subject_mapping
(
    id          int auto_increment comment '主键'
        primary key,
    subject_id  int         null comment '题目id',
    category_id int         null comment '分类id',
    label_id    int         null comment '标签id',
    create_by   varchar(32) null comment '创建人',
    create_time datetime    null comment '创建时间',
    update_by   varchar(32) null comment '更新人',
    update_time datetime    null comment '更新时间',
    is_deleted  int         null comment '是否删除 0未删除 1已删除'
)
    comment '题目分类关联表';

create table subject_multiple
(
    id             int auto_increment
        primary key,
    subject_id     int          null comment '题目id',
    option_type    int          null comment '选项类型（A B C D E）',
    option_content varchar(128) null comment '选项内容',
    is_correct     int          null comment '是否正确',
    create_by      varchar(32)  null comment '创建人',
    create_time    datetime     null comment '创建时间',
    update_by      varchar(32)  null comment '更新人',
    update_time    datetime     null comment '更新时间',
    is_deleted     int          null comment '是否删除 0未删除|1已删除'
)
    comment '多选题表';

create table subject_radio
(
    id             int auto_increment
        primary key,
    subject_id     int          null comment '题目id',
    option_type    int          null comment '选项类型（A B C D E）',
    option_content varchar(128) null comment '选项内容',
    is_correct     int          null comment '是否正确',
    create_by      varchar(32)  null comment '创建人',
    create_time    datetime     null comment '创建时间',
    update_by      varchar(32)  null comment '更新人',
    update_time    datetime     null comment '更新时间',
    is_deleted     int          null comment '是否删除 0未删除|1已删除'
)
    comment '单选题表';

create table user_operation
(
    id               bigint        not null comment '主键'
        primary key,
    user_id          bigint        not null comment '用户ID',
    operation_type   int           null comment '答题1|仿真实验2|场景实验3|发布4',
    operation_result varchar(255)  null comment '操作结果',
    details          varchar(500)  null comment '操作详情',
    created_by       varchar(32)   null comment '创建人',
    created_time     datetime      null comment '创建时间',
    update_by        varchar(32)   null comment '更新人',
    update_time      datetime      null comment '更新时间',
    is_deleted       int default 0 null comment '是否被删除 0为删除 1已删除'
)
    comment '用户操作表';

