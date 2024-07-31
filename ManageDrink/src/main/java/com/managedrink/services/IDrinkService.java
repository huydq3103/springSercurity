package com.managedrink.services;

import com.managedrink.dto.DrinkDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

public interface IDrinkService {

    DrinkDTO createDrink(DrinkDTO drinkDTO);

    Page<DrinkDTO> getDrinks(Pageable pageable);

    DrinkDTO updateDrink(DrinkDTO drinkDTO);

    String deleteDrink(Long id);

}
