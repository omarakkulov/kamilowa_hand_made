package ru.akkulov.kamilowa_hand_made.config;

import lombok.extern.slf4j.Slf4j;
import org.jooq.codegen.GenerationTool;
import org.jooq.meta.jaxb.Configuration;
import org.jooq.meta.jaxb.Database;
import org.jooq.meta.jaxb.Generator;
import org.jooq.meta.jaxb.Jdbc;
import org.jooq.meta.jaxb.Target;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.lang.NonNull;

@Slf4j
public final class JooqRecordsGeneratorConfigUtils {

  @Value("${jooq.database.name}")
  private static String JOOQ_DATABASE_NAME;
  @Value("${jooq.target.package.name}")
  private static String JOOQ_PACKAGE_NAME;
  @Value("${jooq.target.directory}")
  private static String JOOQ_TARGET_DIRECTORY;


  private JooqRecordsGeneratorConfigUtils() {
  }

  /**
   * Сгенерировать классы-рекорды в JOOQ.
   *
   * @param datasourceUrl      url для доступа к  БД
   * @param datasourceUsername логин
   * @param datasourcePassword пароль
   */
  public static void generateJooqSqlRecords(@NonNull String datasourceUrl, @NonNull String datasourceUsername,
      @NonNull String datasourcePassword) {
    Configuration configuration = new Configuration()
        .withJdbc(new Jdbc()
            .withUrl(datasourceUrl)
            .withUser(datasourceUsername)
            .withPassword(datasourcePassword))
        .withGenerator(new Generator()
            .withDatabase(new Database()
                .withName(JOOQ_DATABASE_NAME)
                .withIncludes(".*")
                .withExcludes("")
                .withInputSchema("public"))
            .withTarget(new Target()
                .withPackageName(JOOQ_PACKAGE_NAME)
                .withDirectory(JOOQ_TARGET_DIRECTORY)));

    try {
      GenerationTool.generate(configuration);
      log.info("Успешная генерация записей в Jooq");
    } catch (Exception e) {
      log.error("Ошибка генерации записей в Jooq");
      throw new IllegalStateException(e.getMessage());
    }
  }
}
