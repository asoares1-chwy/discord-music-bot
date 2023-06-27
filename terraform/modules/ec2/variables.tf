variable "ec2_name" {
  type    = string
  default = "discord-music-bot"
}

variable "ec2_security_group_name" {
  type    = string
  default = "discord-music-bot-sg"
}

variable "ec2_security_group_description" {
  type    = string
  default = "security group for discord music bot"
}

variable "ec2_ami" {
  description = "Amazon Linux 2 Kernel 5.10 AMI 2.0.20230221.0 x86_64 HVM gp2"
  type        = string
  default     = "ami-006dcf34c09e50022"
}

variable "ec2_instance_type" {
  type    = string
  default = "t1.micro"
}

variable "vpc_id" {
  type = string
}

variable "ec2_ssh_key_name" {
  type    = string
  default = "discord-music-bot-ec2-key"
}

variable "public_subnet_id" {
  description = "The ID of the Public Subnet"
  type        = string
}