variable "vpc_name" {
  type        = string
  default     = "discord-music-bot-vpc"
}

variable "vpc_cidr_block" {
  description = "The IPv4 CIDR block of the VPC"
  type        = string
  default     = "10.0.0.0/16"
}

variable "vpc_enable_dns_hostnames" {
  type        = bool
  default     = true
}

variable "vpc_enable_dns_support" {
  type        = bool
  default     = true
}
