package com.example.ECommerce.dto.AppUser;


import com.example.ECommerce.entity.Order;

import java.util.List;

public record AppUserRecord(String username, String role, List<Order> orders) {}
