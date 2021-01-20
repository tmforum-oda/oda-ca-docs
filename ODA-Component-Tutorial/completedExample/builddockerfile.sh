docker build -t lesterthomas/productcatalog:0.1 -t lesterthomas/productcatalog:latest  -f dockerfile .
docker push lesterthomas/productcatalog --all-tags