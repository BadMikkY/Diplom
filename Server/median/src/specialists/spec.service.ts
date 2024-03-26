import { Injectable } from '@nestjs/common';
import * as bcrypt from 'bcrypt';
import { PrismaClient } from '@prisma/client';

const prisma = new PrismaClient();

@Injectable()
export class SpecService {
  async createSpec(data: any) {
    const hashedPassword = await bcrypt.hash(data.Password, 10);

    const spec = await prisma.specialist.create({
      data: {
        ...data,
        Password: hashedPassword,
      },
    });
    return spec;
  }

  async loginSpec(email: string, password: string) {
    const spec = await prisma.specialist.findUnique({
      where: {
        Email: email,
      },
    });

    if (!spec) {
      throw new Error('Работник с такой почтой не найден');
    }
    const isPasswordValid = await bcrypt.compare(password, spec.Password);

    if (!isPasswordValid) {
      throw new Error('Неверный пароль');
    }

    return spec;
  }

  async getSpec(id: number) {
    const spec = await prisma.specialist.findUnique({
      where: {
        SpecialistID: id,
      },
    });
    return spec;
  }

  async findSpecialistsByNameAndSkills(name: string, skills: string) {
    let whereClause = {};

    if (name) {
      whereClause = {
        ...whereClause,
        SpecName: {
          contains: name,
          mode: 'insensitive',
        },
      };
    }

    if (skills) {
      whereClause = {
        ...whereClause,
        Skills: {
          contains: skills,
          mode: 'insensitive',
        },
      };
    }

    const specialists = await prisma.specialist.findMany({
      where: whereClause,
      select: {
        SpecialistID: true,
        Email: true,
        Skills: true,
        SpecName: true,
        Experience: true,
        Schedule: true,
        Rates: true,

      },
    });

    return specialists;
  }

  async addReviewAndRating(UserID: number, SpecialistID: number, ReviewText: string, Rating: string) {
    const newReview = await prisma.review.create({
      data: {
        Rating: Rating,
        ReviewText: ReviewText,
        User: {
          connect: {
            UserID: UserID
          }
        },
        Specialist: {
          connect: {
            SpecialistID: SpecialistID
          }
        }
      },
    });
    return newReview;
  }

  async getReviewsBySpecialistId(specialistId: number) {
    const review = await prisma.review.findMany({
      where: {
        SpecialistID: specialistId,
      },
    });
    return review;
  }
}