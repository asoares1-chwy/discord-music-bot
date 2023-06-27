variable "route_table_name" {
  description = "The Name of the Route Table"
  type        = string
  default     = "Free Tier Route Table"
}

variable "vpc_id" {
  description = "The ID of the VPC"
  type        = string
}

variable "internet_gateway_id" {
  description = "The ID of the Internet Gateway"
  type        = string
}

variable "public_subnet_id" {
  description = "The ID of the Public Subnet"
  type        = string
}

variable "subnet_name" {
  description = "The Name of the Public Subnet"
  type        = string
  default     = "Free Tier Public Subnet"
}

variable "subnet_cidr_block" {
  description = "The IPv4 CIDR block of the Public Subnet"
  type        = string
  default     = "10.0.1.0/24"
}

variable "subnet_availability_zone" {
  description = "The Availability Zone of the Public Subnet"
  type        = string
  default     = "us-east-1a"
}
