-- DropForeignKey
ALTER TABLE "Specialist" DROP CONSTRAINT "Specialist_SpecialistID_fkey";

-- AlterTable
CREATE SEQUENCE specialist_specialistid_seq;
ALTER TABLE "Specialist" ALTER COLUMN "SpecialistID" SET DEFAULT nextval('specialist_specialistid_seq');
ALTER SEQUENCE specialist_specialistid_seq OWNED BY "Specialist"."SpecialistID";
