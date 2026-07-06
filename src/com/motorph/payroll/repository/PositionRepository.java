package com.motorph.payroll.repository;

import com.motorph.payroll.model.Position;

import java.util.List;

public interface PositionRepository {
    List<Position> findAll();
}
