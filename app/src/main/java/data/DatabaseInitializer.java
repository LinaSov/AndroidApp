package data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class DatabaseInitializer {

    public static void initializeDefaultData(SQLiteDatabase db) {
        // Insert initial data

        insertExercise(db, "Прыжки на месте", "Делая прыжки на месте. Эффективное кардио для улучшения общей выносливости.", 0, 15);
        insertExercise(db, "Бег на месте", "Бег на месте . Прекрасное кардио-упражнение для активации ног и сердечно-сосудистой системы.", 0, 12);
        insertExercise(db, "Бёрпи", "Сложное упражнение, включающее прыжок, отжимание и приседание. Предоставляет комплексную нагрузку на различные группы мышц.", 0, 19);
        insertExercise(db, "Прыжки в стороны", "Прыгайте в стороны, раскачивая руки. Улучшает координацию и силу ног.", 0, 15);
        insertExercise(db, "Бег с высоким подниманием бедра", "Поднимайте колени к груди в беге на месте. Укрепляет пресс и активирует ноги.", 0, 15);
        insertExercise(db, "Планка с подтягиванием колен", "Примите положение планка и подтягивайте колени к груди. Развивает силу кора и тренирует верхнюю часть тела.", 0, 15);
        insertExercise(db, "Бег с высокими прыжками", "Бег с высокими прыжками на каждом шаге. Улучшает силу ног и выносливость.", 0, 15);
        insertExercise(db, "Прыжки вперед и назад", "Прыгайте вперед и назад, сохраняя темп. Повышает частоту сердечных сокращений и координацию.", 0, 15);
        insertExercise(db, "Прыжки с разворотом", "Прыгайте с разворотом тела в воздухе. Эффективно тренирует ноги и улучшает координацию.", 0, 15);
        insertExercise(db, "Бег с боковыми прыжками", "Бег с боковыми прыжками через каждые несколько шагов. Укрепляет боковые мышцы и сердечно-сосудистую систему.", 0, 10);
        insertExercise(db, "Подъемы колен в бок", "Поднимайте колени в стороны в беге на месте. Тренирует внутреннюю часть бедер и улучшает баланс.", 0, 15);
        insertExercise(db, "Джампинг джек", "Разводите руки и ноги в стороны при прыжке вверх. Эффективное кардио с минимальной нагрузкой на суставы.", 0, 10);
        insertExercise(db, "Бег в звезде", "Бег на месте с разводом рук и ног в стороны. Улучшает координацию и активирует верхнюю часть тела.", 0, 15);
        insertExercise(db, "Прыжки на одной ноге", "Прыгайте на одной ноге, меняя их через некоторое время. Развивает силу и стабильность ног.", 0, 15);

// Упражнения на пресс и корпус(силовые)
        insertExercise(db, "Скручивания лёжа на полу", "Лежа на спине, поднимайте верхнюю часть тела, стараясь приподнять плечи от пола.", 1, 6);
        insertExercise(db, "Велосипед лёжа на полу", "Имитация движений велосипеда, лежа на спине. Поднимайте ноги и вращайте их, дотрагиваясь коленями до противоположного локтя.", 1, 10);
        insertExercise(db, "Поднятие ног на 90 градусов", "Лежа на спине, поднимайте прямые ноги на 90 градусов от пола, затем опускайте их обратно.", 1, 8);
        insertExercise(db, "Поднятие таза от пола в берёзке", "Лежа на спине с поднятыми ногами, поднимайте таз вверх, создавая прямую линию от плеч до коленей.", 1, 12);
        insertExercise(db, "Жук", "Лежа на спине, поднимайте верхнюю и нижнюю части тела, создавая движение, похожее на полёт жука.", 1, 8);
        insertExercise(db, "Планка на прямых руках", "Стойте в планке, опираясь на прямые руки. Удерживайте положение, напрягая корпус.", 1, 5);
        insertExercise(db, "Скручивание в планке на локтях", "Находясь в планке на локтях, скручивайте корпус, принося одно колено к противоположному локтю.", 1, 7);
        insertExercise(db, "Платнка с согнутыми под 90 градусов коленями", "Стойте в планке, согнув колени под углом 90 градусов. Укрепляйте корпус и ноги.", 1, 6);
        insertExercise(db, "Русский твист", "Сидя на полу, скручивайте корпус из стороны в сторону, поднимая ноги от пола. Движение напоминает вращение корпуса в боксе.", 1, 9);
        insertExercise(db, "Скручивание с касанием пяток", "Лежа на спине, поднимайте верхнюю часть тела и старайтесь дотронуться до пяток руками.", 1, 8);

        // Упражнения на ягодицы (силовые)
        insertExercise(db, "Отведение ноги назад", "Стоя на четвереньках, отводите ногу, согнутую в угол 90 градусов, назад.", 1, 5);
        insertExercise(db, "Отведение ноги в сторону", "Стоя на четвереньках, отводите ногу в сторону, удерживая ее в углу 90 градусов.", 1, 6);
        insertExercise(db, "Пульсация ноги в верхней точке", "Стойте на четвереньках, пульсируйте ногой в верхней точке отведения.", 1, 7);
        insertExercise(db, "Перевод прямой ноги", "Стоя на четвереньках, поднимите прямую ногу и переведите ее в сторону.", 1, 8);
        insertExercise(db, "Ягодичный мостик", "Лежа на спине, поднимайте таз вверх, создавая прямую линию от плеч до коленей.", 1, 7);
        insertExercise(db, "Яготичный мостик с пульсацией в верхней точке", "Лежа на спине, поднимайте таз вверх, затем выполняйте пульсации в верхней точке движения.", 1, 9);
        insertExercise(db, "Поднятие согнутых ног лёжа на полу", "Лежа на спине, поднимайте согнутые в колене ноги вверх.", 1, 6);
        insertExercise(db, "Вращение ногой по кругу лёжа на боку", "Лежа на боку, поднимайте ногу и выполняйте вращательные движения по кругу.", 1, 8);

        // Упражнения на растяжку
        insertExercise(db, "Растяжка ног сидя", "Сидите на полу, выпрямите ноги и наклоняйтесь вперед, стараясь дотронуться к пальцам ног.", 2, 3);
        insertExercise(db, "Растяжка пресса", "Лежа на спине, согните одну ногу и прижмите ее к груди, удерживая позу.", 2, 4);
        insertExercise(db, "Растяжка спины стоя", "Стойте прямо, поднимите руки вверх и наклоняйтесь в сторону, создавая растяжку в боковой части тела.", 2, 5);
        insertExercise(db, "Растяжка плеч", "Поднимите одну руку вверх, согните ее в локте, затем другой рукой потянитесь за локоть, создавая растяжку в плече.", 2, 3);
        insertExercise(db, "Растяжка шеи", "Сидите на стуле, наклоняйте голову в стороны и вперед, создавая растяжку в шее и верхней части спины.", 2, 2);
        insertExercise(db, "Растяжка бедер", "Сядьте на пол с расставленными ногами, наклоняйтесь вперед, стараясь дотронуться к полу между ног.", 2, 4);
        insertExercise(db, "Растяжка икры", "Встаньте, одну ногу отведите назад, вытяните пятку в пол и наклонитесь вперед, чувствуя растяжение в икре.", 2, 3);
        insertExercise(db, "Растяжка бедер в положении лотоса", "Сядьте в положение лотоса, держа стопы вместе и медленно наклоняйтесь вперед, создавая растяжку в бедрах.", 2, 4);
        insertExercise(db, "Растяжка спины лежа", "Лежа на спине, согните одну ногу и поверните тело в сторону противоположной ноги, создавая растяжку в спине.", 2, 5);
        insertExercise(db, "Растяжка рук", "Встаньте прямо, поднимите руку вверх, согните ее в локте и попытайтесь дотронуться к верхней части спины ладонью другой руки.", 2, 3);

        // Балетный станок
        insertExercise(db, "Demi-plie", "Положение стопы на полу ровное, упор стопы на большой палец не допустим. Пятки должны плотно прилегать к полу, способствуя развитию голеностопного сустава. Корпус прямой и подтянутый, плечи и бедра ровные. Центр тяжести корпуса равномерно распределяется на обе ноги. Плавное и равномерное приседание.", 3, 4);
        insertExercise(db, "Battement tendu", "Движение выполняется по первой и пятой позиции ног. Исполняется в направлении вперед, в сторону, назад. Наклон вперед или назад. Бросок ноги в воздух и возвращение в позицию.", 3, 5);
        insertExercise(db, "Battement tendu jete", "Движение выполняется по правилам battement tendu, но с броском в воздух. Рабочая нога бросает ногу на высоту 25-45 градусов и активно возвращается в позицию. Точное и равномерное движение.", 3, 6);
        insertExercise(db, "Rond de jambe par terre", "В passé par terre рабочая нога натянута и выворотна. Во время passé стопа рабочей ноги равномерно располагается на полу, избегая упора на большой палец. Корпус подтянут и спокоен. Определенные движения ногой в полукруге.", 3, 5);
        insertExercise(db, "Por De Bras", "Следить за подтянутостью корпуса. Наклоны вперед и назад, вращения рук в первой, второй и третьей позициях. Плавные и изящные движения.", 3, 4);
        insertExercise(db, "Battement frappe", "Выпрямление и сгибание ноги в этом упражнении следует равномерно, точно придерживаясь направления второй позиции. Резкое и четкое движение.", 3, 5);
        insertExercise(db, "Battement fondu", "Движение в пол, на 45 и 90 градусов вперед, в сторону, назад. Мягкое и эластичное исполнение для развития прыжков.", 3, 4);
        insertExercise(db, "Battement releve lent", "Выполняется в медленном темпе. Плавное движение с подтянутым корпусом и натянутой опорной ногой.", 3, 5);
        insertExercise(db, "Battement developpe", "Рабочая нога выворотна, опорная нога натянута и выворотна. Плавные движения с поддержкой корпуса.", 3, 6);
        insertExercise(db, "Grand battement jete", "Нога делает бросок на 90 градусов и выше. Исполняется в сторону, вперед, назад по первой, пятой позициям.", 3, 7);
    }

    private static void insertExercise(SQLiteDatabase db, String name, String description, int training, int calories) {
        ContentValues values = new ContentValues();
        values.put(addwork.GuestEntry.COLUMN_NAME, name);
        values.put(addwork.GuestEntry.COLUMN_DESCRIPTION, description);
        values.put(addwork.GuestEntry.COLUMN_TREINING, training);
        values.put(addwork.GuestEntry.COLUMN_CALORIES, calories);

        db.insert(addwork.GuestEntry.TABLE_NAME, null, values);
    }
}