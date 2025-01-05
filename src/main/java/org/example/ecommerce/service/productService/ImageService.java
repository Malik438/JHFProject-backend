package org.example.ecommerce.service.productService;

import org.example.ecommerce.model.productModel.Image;
import org.example.ecommerce.reopsotries.productRepo.ImageRepository;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private  final ImageRepository imageRepository;

    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }



    public  void save(Image image) {
        imageRepository.save(image);
    }


}
