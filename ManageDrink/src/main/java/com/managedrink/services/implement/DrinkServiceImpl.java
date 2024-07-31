package com.managedrink.services.implement;

import com.managedrink.dto.DrinkDTO;
import com.managedrink.entity.DrinkEntity;
import com.managedrink.entity.ToppingEntity;
import com.managedrink.exception.NotFoundException;
import com.managedrink.exception.NotNullException;
import com.managedrink.repository.DrinkRepository;
import com.managedrink.repository.ToppingRepository;
import com.managedrink.services.IDrinkService;
import com.managedrink.until.constants.MessageConstant;
import com.managedrink.until.mapper.DrinkMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@Slf4j
/**
 * Implementations of Drink Service
 */
public class DrinkServiceImpl implements IDrinkService {

    @Autowired
    private DrinkRepository drinkRepository;

    @Autowired
    private ToppingRepository toppingRepository;

    @Autowired
    private MessageServiceImpl messageService;


    /**
     * Tạo mới một đồ uống từ DTO.
     *
     * @param drinkDTO Đối tượng DTO của đồ uống để tạo mới
     * @return Đối tượng DTO của đồ uống đã được tạo mới
     * @throws NotNullException Nếu drinkDTO là null
     */
    @Override
    @Transactional
    public DrinkDTO createDrink(DrinkDTO drinkDTO) {
        if (Objects.isNull(drinkDTO)) {
            throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_NOT_NULL));
        }
         if(Objects.isNull(drinkDTO.getListIds())){
             throw new NotNullException(messageService.getMessage(MessageConstant.LIST_TOPPING_NOT_NULL));
         }

        DrinkEntity drinkEntity = DrinkMapper.convertDTOToEntity(drinkDTO);
        drinkEntity.setCreateDate(LocalDate.now()); // Thiết lập ngày tạo là ngày hiện tại

        List<ToppingEntity> toppingEntities = toppingRepository.findAllById(drinkDTO.getListIds());
        drinkEntity.setToppings(toppingEntities);

        drinkEntity = drinkRepository.save(drinkEntity);

        DrinkDTO result = DrinkMapper.convertEntityTODTO(drinkEntity);
        result.setListIds(drinkDTO.getListIds());
        return result;
    }

    /**
     * Lấy danh sách các đồ uống phân trang.
     *
     * @param pageable Thông tin phân trang
     * @return Trang các DTO của đồ uống
     */
    @Override
    public Page<DrinkDTO> getDrinks(Pageable pageable) {
        Page<DrinkEntity> drinkEntities = drinkRepository.findAll(pageable);
        return drinkEntities.map(DrinkMapper::convertEntityTODTO);
    }

    /**
     * Cập nhật thông tin của một đồ uống.
     *
     * @param drinkDTO Đối tượng DTO của đồ uống để cập nhật thông tin
     * @return Đối tượng DTO của đồ uống đã được cập nhật
     * @throws NotNullException Nếu drinkDTO là null
     */
    @Override
    @Transactional
    public DrinkDTO updateDrink(DrinkDTO drinkDTO) {
        if (Objects.isNull(drinkDTO)) {
            throw new NotNullException(MessageConstant.DRINK_NOT_NULL) ;
        }

        if(Objects.isNull(drinkDTO.getId())){
             throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_ID_NOT_NULL));
        }

        DrinkEntity drinkEntity = drinkRepository.findById(drinkDTO.getId())
                .orElseThrow(() -> new NotFoundException(messageService.getMessage(MessageConstant.DRINK_NOT_FOUND)));

        drinkEntity.setNameDrink(drinkDTO.getNameDrink());
        drinkEntity.setDescription(drinkDTO.getDescription());

        drinkEntity.setToppings(toppingRepository.findAllById(drinkDTO.getListIds()));
        drinkRepository.save(drinkEntity);

        return drinkDTO;
    }

    /**
     * Xóa một đồ uống dựa trên ID.
     *
     * @param id ID của đồ uống cần xóa
     * @return ResponseEntity chứa thông tin về việc xóa đồ uống và HttpStatus tương ứng
     * @throws NotFoundException Nếu không tìm thấy đồ uống với ID đã cho
     */
    @Override
    @Transactional
    public String deleteDrink(Long id) {
        if(Objects.isNull(id)){
             throw new NotNullException(messageService.getMessage(MessageConstant.DRINK_ID_NOT_NULL));
        }
        if (!drinkRepository.existsById(id)) {
            throw new NotFoundException(messageService.getMessage(MessageConstant.DRINK_NOT_FOUND));
        }

        drinkRepository.deleteById(id);
        return messageService.getMessage(MessageConstant.DRINK_DELETED_SUCCESSFULLY);
    }
}
