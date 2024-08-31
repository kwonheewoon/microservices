package org.khw.productservice.product.services

import lombok.RequiredArgsConstructor
import org.khw.api.composite.core.product.Product
import org.khw.api.composite.core.product.ProductService
import org.khw.util.exceptions.InvalidInputException
import org.khw.util.exceptions.NotFoundException
import org.khw.util.http.ServiceUtil
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.RestController

@RestController
@RequiredArgsConstructor
class ProductServiceImpl(
    private val serviceUtil: ServiceUtil
) : ProductService {

    override fun getProduct(productId: Int): Product {
        LOG.debug("/product return the found product for productId={}", productId)

        if (productId < 1) throw InvalidInputException("Invalid productId: $productId")

        if (productId == 13) throw NotFoundException("No product found for productId: $productId")

        return Product(productId, "name-$productId", 123, serviceUtil.serviceAddress)
    }

    companion object {
        private val LOG: Logger = LoggerFactory.getLogger(ProductServiceImpl::class.java)
    }
}