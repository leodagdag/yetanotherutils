package com.yet.another.utils.collections;

import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatIllegalArgumentException;

public class ListUtilsTest {


    private List<Integer> generateList(final int size) {
        return IntStream.range(0, size)
                .map(idx -> ThreadLocalRandom.current().nextInt())
                .boxed()
                .collect(Collectors.toList());
    }


    @Test
    public void partitionArrayList() throws Exception {
        final List<Integer> data = generateList(4);
        final int partitionSize = 2;

        final int expectedSize = 2;
        final List<List<Integer>> expected = Lists.newArrayList(
                new LinkedList<>(data.subList(0, 2)),
                new LinkedList<>(data.subList(2, 4))
        );

        final List<List<Integer>> result = ListUtils.partition(data, partitionSize);
        assertThat(result)
                .hasSize(expectedSize)
                .containsExactlyElementsOf(expected);
    }

    @Test
    public void partitionWithSupplier() throws Exception {
        final List<Integer> data = generateList(5);
        final int partitionSize = 2;
        final int expectedSize = 3;
        final List<List<Integer>> expected = Lists.newArrayList(
                new LinkedList<>(data.subList(0, 2)),
                new LinkedList<>(data.subList(2, 4)),
                new LinkedList<>(data.subList(4, 5))
        );

        final List<List<Integer>> result = ListUtils.partition((Supplier<List<Integer>>) LinkedList::new).apply(data, partitionSize);
        assertThat(result)
                .hasSize(expectedSize)
                .containsExactlyElementsOf(expected);

    }

    @Test
    public void throwIllegalArgumentExceptionIfListIsNull() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> ListUtils.partition(null, 1))
                .withMessage("list must be not null")
                .withNoCause();
    }

    @Test
    public void throwIllegalArgumentExceptionIfSizeIsEqualTo0() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> ListUtils.partition(generateList(5), 0))
                .withMessageEndingWith("must be greater than 0")
                .withNoCause();
    }

    @Test
    public void throwIllegalArgumentExceptionIfSizeIsNegative() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> ListUtils.partition(generateList(5), -1))
                .withMessageEndingWith("must be greater than 0")
                .withNoCause();
    }

    @Test
    public void throwIllegalArgumentExceptionIfPartitionSizeIsGreaterThanListSize() throws Exception {
        assertThatIllegalArgumentException().isThrownBy(() -> ListUtils.partition(generateList(5), Integer.MAX_VALUE))
                .withMessageContaining("must be greater or equal to partitionSize")
                .withNoCause();
    }
}