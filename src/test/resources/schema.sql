-- address type
create table oms.address_type (
	address_type_id binary(16) not null primary key,
	address_type_code varchar(16) not null,
	address_type_name varchar(32) not null,
	create_ts timestamp not null,
	update_ts timestamp not null default current_timestamp
);

-- contact type
create table oms.contact_type (
	contact_type_id binary(16) not null primary key,
	contact_type_code varchar(16) not null,
	contact_type_name varchar(32) not null,
	create_ts timestamp not null,
	update_ts timestamp not null default current_timestamp
);

-- user
create table oms.users (
	user_id binary(16) not null primary key,
	user_name varchar(20) not null,
	last_login_ts timestamp,
	is_soft_deleted tinyint(1) not null default 0,
	create_ts timestamp not null,
	update_ts timestamp not null default current_timestamp
);

-- user profile
create table oms.user_profile (
    user_id binary(16) not null primary key,
    first_name varchar(32) not null,
    last_name varchar(32) not null,
    date_of_birth date not null,
    age int not null,
    sex enum('male', 'female', 'other') not null,
    email varchar(64) not null,
	create_ts timestamp not null,
	update_ts timestamp not null default current_timestamp,
	constraint `fk_user_profile_user`
	    foreign key (user_id) references oms.users (user_id)
	    on delete cascade
	    on update cascade
);

-- user address
create table oms.user_address (
    user_address_id binary(16) not null primary key,
    address_type_id binary(16) not null,
    country_code char(2) not null,
    state varchar(64) not null,
    district varchar(64),
    city varchar(64),
    po_box varchar(32),
    user_id binary(16) not null,
    create_ts timestamp not null,
	update_ts timestamp not null default current_timestamp,
	constraint `fk_user_address_user`
    	    foreign key (user_id) references oms.users (user_id)
    	    on delete no action
    	    on update no action,
    constraint `fk_user_address_address_type`
    	    foreign key (address_type_id) references oms.address_type (address_type_id)
    	    on delete no action
    	    on update no action
);

-- user contact
create table oms.user_contact (
	user_contact_id binary(16) not null primary key,
    contact_type_id binary(16) not null,
	user_contact_number varchar(64) not null,
	user_id binary(16) not null,
    create_ts timestamp not null,
    update_ts timestamp not null default current_timestamp,
    constraint `fk_user_contact_user`
        foreign key (user_id) references oms.users (user_id)
        on delete no action
        on update no action,
    constraint `fk_user_contact_contact_type`
        foreign key (contact_type_id) references oms.contact_type (contact_type_id)
        on delete no action
        on update no action
);

-- user orders
-- order
-- order line item
-- product



