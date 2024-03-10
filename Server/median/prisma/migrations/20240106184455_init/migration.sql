-- CreateTable
CREATE TABLE "User" (
    "UserID" SERIAL NOT NULL,
    "UserName" TEXT NOT NULL,
    "Email" TEXT NOT NULL,
    "Password" TEXT NOT NULL,
    "Role" TEXT NOT NULL,

    CONSTRAINT "User_pkey" PRIMARY KEY ("UserID")
);

-- CreateTable
CREATE TABLE "Service" (
    "ServiceID" SERIAL NOT NULL,
    "ServiceName" TEXT NOT NULL,
    "Description" TEXT NOT NULL,

    CONSTRAINT "Service_pkey" PRIMARY KEY ("ServiceID")
);

-- CreateTable
CREATE TABLE "Specialist" (
    "SpecialistID" INTEGER NOT NULL,
    "Skills" TEXT NOT NULL,
    "Experience" TEXT NOT NULL,
    "Schedule" TEXT NOT NULL,
    "Rates" TEXT NOT NULL,

    CONSTRAINT "Specialist_pkey" PRIMARY KEY ("SpecialistID")
);

-- CreateTable
CREATE TABLE "Booking" (
    "BookingID" SERIAL NOT NULL,
    "UserID" INTEGER NOT NULL,
    "SpecialistID" INTEGER NOT NULL,
    "BookingDate" TIMESTAMP(3) NOT NULL,
    "Status" TEXT NOT NULL,
    "ReviewID" INTEGER NOT NULL,

    CONSTRAINT "Booking_pkey" PRIMARY KEY ("BookingID")
);

-- CreateTable
CREATE TABLE "Review" (
    "ReviewID" SERIAL NOT NULL,
    "UserID" INTEGER NOT NULL,
    "SpecialistID" INTEGER NOT NULL,
    "Rating" TEXT NOT NULL,
    "ReviewText" TEXT NOT NULL,

    CONSTRAINT "Review_pkey" PRIMARY KEY ("ReviewID")
);

-- CreateTable
CREATE TABLE "WorkProgress" (
    "ProgressID" SERIAL NOT NULL,
    "BookingID" INTEGER NOT NULL,
    "Percentage" INTEGER NOT NULL,
    "UpdateTime" TIMESTAMP(3) NOT NULL,

    CONSTRAINT "WorkProgress_pkey" PRIMARY KEY ("ProgressID")
);

-- CreateIndex
CREATE UNIQUE INDEX "User_Email_key" ON "User"("Email");

-- CreateIndex
CREATE UNIQUE INDEX "WorkProgress_BookingID_key" ON "WorkProgress"("BookingID");

-- AddForeignKey
ALTER TABLE "Specialist" ADD CONSTRAINT "Specialist_SpecialistID_fkey" FOREIGN KEY ("SpecialistID") REFERENCES "User"("UserID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Booking" ADD CONSTRAINT "Booking_UserID_fkey" FOREIGN KEY ("UserID") REFERENCES "User"("UserID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Booking" ADD CONSTRAINT "Booking_SpecialistID_fkey" FOREIGN KEY ("SpecialistID") REFERENCES "Specialist"("SpecialistID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Review" ADD CONSTRAINT "Review_UserID_fkey" FOREIGN KEY ("UserID") REFERENCES "User"("UserID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "Review" ADD CONSTRAINT "Review_SpecialistID_fkey" FOREIGN KEY ("SpecialistID") REFERENCES "Specialist"("SpecialistID") ON DELETE RESTRICT ON UPDATE CASCADE;

-- AddForeignKey
ALTER TABLE "WorkProgress" ADD CONSTRAINT "WorkProgress_BookingID_fkey" FOREIGN KEY ("BookingID") REFERENCES "Booking"("BookingID") ON DELETE RESTRICT ON UPDATE CASCADE;
