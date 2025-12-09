package com.yaloys.products.grpc;

import com.yaloys.products.grpc.product.ProductServiceGrpc;
import com.yaloys.products.grpc.product.ProductRequest;
import com.yaloys.products.grpc.product.ProductsRequest;
import com.yaloys.products.grpc.product.ProductResponse;
import com.yaloys.products.grpc.product.ProductsResponse;
import com.yaloys.products.models.Product;
import com.yaloys.products.repositories.ProductRepository;
import io.grpc.stub.StreamObserver;
import io.quarkus.grpc.GrpcService;
import jakarta.inject.Inject;

@GrpcService
public class ProductGrpcService extends ProductServiceGrpc.ProductServiceImplBase {

    @Inject
    ProductRepository productRepository;

    @Override
    public void getProduct(ProductRequest request, StreamObserver<ProductResponse> responseObserver) {
        Product product = productRepository.findById(request.getId());

        if (product != null) {
            ProductResponse response = ProductResponse.newBuilder().setId(product.getProductId()).setName(product.getName())
                    .setPrice(product.getPrice().toString()).build();

            responseObserver.onNext(response);
            responseObserver.onCompleted();
        }
        else {
            responseObserver.onError(new RuntimeException("Product not found"));
        }
    }

    @Override
    public void getProducts(ProductsRequest request, StreamObserver<ProductsResponse> responseObserver) {
        ProductsResponse.Builder responseBuilder = ProductsResponse.newBuilder();

        for (Integer id : request.getIdsList()) {
            Product product = productRepository.findById(id);
            if (product != null) {
                ProductResponse productResponse = ProductResponse.newBuilder().setId(product.getProductId()).setName(product.getName())
                        .setPrice(product.getPrice().toString()).build();

                responseBuilder.addProducts(productResponse);
            }
        }
        responseObserver.onNext(responseBuilder.build());
        responseObserver.onCompleted();
    }
}