provider "aws" {
  region = var.region
#   profile = var.profile
}

#terraform {
#  backend "s3" {}
#}

module "public_subnet" {
  source = "./modules/public-subnet"
  vpc_id = module.vpc.vpc_id
}

module "internet_gateway" {
  source = "./modules/internet-gateway"
  vpc_id = module.vpc.vpc_id
}

module "route_table" {
  source = "./modules/route-table"

  vpc_id              = module.vpc.vpc_id
  internet_gateway_id = module.internet_gateway.internet_gateway_id
  public_subnet_id    = module.public_subnet.public_subnet_id
}

module "vpc" {
  source = "./modules/vpc"
}

module "ec2" {
  source = "./modules/ec2"
  vpc_id = module.vpc.vpc_id
  public_subnet_id        = module.public_subnet.public_subnet_id
}
