resource "aws_instance" "discord_music_bot" {
  count         = 1
  ami           = var.ec2_ami
  instance_type = var.ec2_instance_type

  vpc_security_group_ids      = [aws_security_group.ec2_security_group.id]
  associate_public_ip_address = true
  key_name                    = aws_key_pair.ec2_key_pair.key_name
  subnet_id                   = var.public_subnet_id

  tags = {
    name = "discord-music-bot"
  }
}

resource "aws_security_group" "ec2_security_group" {
  name        = var.ec2_security_group_name
  description = var.ec2_security_group_description

  vpc_id = var.vpc_id

  ingress {
    from_port   = 50000
    to_port     = 65535
    protocol    = "udp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name = var.ec2_security_group_name
  }
}

# Warning: Key stored in your tf state!
resource "tls_private_key" "discord_music_bot_key" {
  algorithm = "RSA"
  rsa_bits  = 4096
}

// This must be created manually first, per Terraform documentation
resource "aws_key_pair" "ec2_key_pair" {
  key_name   = var.ec2_ssh_key_name
  public_key = tls_private_key.discord_music_bot_key.public_key_openssh
}
